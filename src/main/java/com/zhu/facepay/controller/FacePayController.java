package com.zhu.facepay.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.zhu.facepay.config.AliPayBusinessConfig;
import com.zhu.facepay.domain.PayBill;
import com.zhu.facepay.domain.res.ResultData;
import com.zhu.facepay.service.PayService;
import com.zhu.facepay.service.impl.PayBillServiceImpl;
import com.zhu.facepay.utils.QueueCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName IndexController.java
 * @Description TODO
 * @createTime 2022年11月21日 09:55:00
 */
@RestController
@RequestMapping("ali/pay/face")
@Slf4j
public class FacePayController {

    @Resource
    PayService payService;
    @Resource
    AliPayBusinessConfig aliPayBusinessConfig;
    @Resource
    PayBillServiceImpl payBillService;


    /**
     * http://localhost:9001/view/facePay.html
     * @param totalAmount 总金额
     * @param subject 商品名称
     * @param tradeNo 订单号
     * @param notifyUrl 回调地址
     * @return
     */
    @GetMapping("/qccode")
    public ResultData<String> qrCode(String totalAmount, String subject,String tradeNo,String notifyUrl) {
        log.info("qccode info totalAmount:{},subject:{},tradeNo:{},notifyUrl:{}",totalAmount,subject,tradeNo,notifyUrl);
        if (StrUtil.isBlank(tradeNo)) {
            tradeNo = "JY"+ DateUtil.current()+ UUID.fastUUID();
        }


        PayBill payBill = new PayBill();
        payBill.setPayAmount(totalAmount);
        payBill.setOrderNum(tradeNo);
        payBill.setIsPay(false);
        payBill.setNotifyUrl(notifyUrl);
        payBillService.save(payBill);

        return ResultData.success(payService.qrCode(totalAmount, subject,tradeNo));
    }


    @PostMapping ("/notify")
    public String preOrderNotify(HttpServletRequest request) throws AlipayApiException {
        //获取支付宝POST过来反馈信息，将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info("params:{}",params);



        //调用SDK验证签名
        //公钥验签示例代码
        boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayBusinessConfig.getPublicKey(), aliPayBusinessConfig.getCharset(), aliPayBusinessConfig.getSignType());
        //公钥证书验签示例代码
        //   boolean flag = AlipaySignature.rsaCertCheckV1(params,alipayPublicCertPath,"UTF-8","RSA2");

        PayBill oldPayBill = payBillService.getPayBillByNotifyId(params.get("notify_id"));
        if (null != oldPayBill) {
            return "success";
        } else if (signVerified) {
            // TODO 验签成功后
            PayBill payBill = new PayBill();
            payBill.setOrderNum(params.get("out_trade_no"));
            payBill.setIsPay(true);
            payBill.setNotifyId(params.get("notify_id"));
            payBill.setPayerId(params.get("buyer_id"));
            payBill.setSellerEmail(params.get("seller_email"));
            payBill.setSellerId(params.get("seller_id"));
            payBill.setTradeNo(params.get("trade_no"));
            payBill.setBuyerLogonId(params.get("buyer_logon_id"));
            PayBill newPayBill = payBillService.updatePayBill(payBill);

            // push queue
            try {
                QueueCore<PayBill> billQueueCore = new QueueCore<>("NOTIFY_JOB");
                billQueueCore.get("NOTIFY_JOB").push(newPayBill);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "success";
            //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success
        } else {
            // TODO 验签失败则记录异常日志，并在response中返回fail.
            return "fail";
        }

    }


}
