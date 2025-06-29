package com.zhu.facepay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.zhu.facepay.config.AliPayBusinessConfig;
import com.zhu.facepay.domain.AliPayInfo;
import com.zhu.facepay.domain.AliRefundInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Http2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName payUtils.java
 * @Description TODO
 * @createTime 2022年11月21日 10:56:00
 */
@Slf4j
@Component("payUtils")
public class PayUtils {

    @Resource
    private AliPayBusinessConfig aliPayBusinessConfig;
    @Value("${ali.pay.notify_host}")
    private String notifyHost;



    /**
     * 支付回调路径
     */
    private static final String NOTIFY_PATH = "/ali/pay/face/notify";

    /**
     * 预下单
     * @param aliPayInfo
     * @return
     * @throws AlipayApiException
     */
    public String preCreateOrder(AliPayInfo aliPayInfo) throws AlipayApiException {
        AlipayClient alipayClient = aliPayBusinessConfig.getAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(notifyHost+NOTIFY_PATH);
//        request.setNotifyUrl(NOTIFY_test_URL);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", aliPayInfo.getOutTradeNo());
        bizContent.put("total_amount", aliPayInfo.getTotalAmount());
        bizContent.put("subject", aliPayInfo.getSubject());

        request.setBizContent(bizContent.toString());
        log.info("req notifyUrl:{}",request.getNotifyUrl());
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            log.info("res:{}",response.getBody());

            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            return jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");

        } else {
            log.error("res:{}",response.getBody());
            return "";
        }
    }

    /**
     * alipay.trade.refund(统一收单交易退款接口)
     * @param aliRefundInfo
     * @return AlipayTradeRefundResponse
     * @throws AlipayApiException
     */
    public AlipayTradeRefundResponse refund(AliRefundInfo aliRefundInfo) throws AlipayApiException {
        AlipayClient alipayClient = aliPayBusinessConfig.getAlipayClient();
        // 创建请求对象
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        // 设置请求参数
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setTradeNo(aliRefundInfo.getTradeNo()); // 支付宝交易号
        model.setOutTradeNo(aliRefundInfo.getOutTradeNo()); // 商户订单号
        model.setRefundAmount(aliRefundInfo.getRefundAmount()); // 退款金额
        model.setRefundReason(aliRefundInfo.getRefundReason()); // 退款原因
        model.setOutRequestNo(aliRefundInfo.getOutRequestNo()); // 退款请求号
        request.setBizModel(model);

        // 执行请求
        return alipayClient.execute(request);
    }
}
