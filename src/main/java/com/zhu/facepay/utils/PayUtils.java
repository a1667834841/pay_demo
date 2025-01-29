package com.zhu.facepay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.zhu.facepay.config.AliPayBusinessConfig;
import com.zhu.facepay.domain.AliPayInfo;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 正式环境
     */
    private static final String NOTIFY_PROD_URL = "https://ggball.top/notify/ali/pay/face/notify";

    /**
     * 测试环境 需要先开启远程穿透
     */
    private static final String NOTIFY_test_URL = "http://1.15.141.114:9000/ali/pay/face/notify";


    /**
     * 支付回调路径
     */
    private static final String NOTIFY_PATH = "/notify/ali/pay/face/notify";

    /**
     * 预下单
     * @param aliPayInfo
     * @return
     * @throws AlipayApiException
     */
    public String preCreateOrder(AliPayInfo aliPayInfo) throws AlipayApiException {
        AlipayClient alipayClient = aliPayBusinessConfig.getAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(Ip.getPublicIpv4()+NOTIFY_PATH);
//        request.setNotifyUrl(NOTIFY_test_URL);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", aliPayInfo.getOutTradeNo());
        bizContent.put("total_amount", aliPayInfo.getTotalAmount());
        bizContent.put("subject", aliPayInfo.getSubject());

        request.setBizContent(bizContent.toString());
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
}
