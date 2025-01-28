//package com.zhu.facepay.config;
//
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.AlipayConfig;
//import com.alipay.api.DefaultAlipayClient;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.test.context.TestPropertySource;
//
///**
// * @author ggBall
// * @version 1.0.0
// * @ClassName AliPayConfig.java
// * @Description TODO
// * @createTime 2022年11月21日 11:13:00
// */
//@TestPropertySource(value="classpath:/application-business.yml")
//@Data
//@Component
//public class AliPayBusinessConfig {
//
//    @Value("${ali.pay.server_url}")
//    private String serverUrl;
//    @Value("${ali.pay.appId}")
//    private String appId;
//    @Value("${ali.pay.private_key}")
//    private String  privateKey;
//    @Value("${ali.pay.format}")
//    private String format;
//    @Value("${ali.pay.charset}")
//    private String charset;
//    @Value("${ali.pay.public_key}")
//    private String publicKey;
//    @Value("${ali.pay.sign_type}")
//    private String signType;
//
//
//    private AlipayClient alipayClient;
//
//    public AlipayClient aliPayClient() throws AlipayApiException {
//        AlipayConfig alipayConfig = new AlipayConfig();
//        alipayConfig.setServerUrl(serverUrl);
//        alipayConfig.setAppId(appId);
//        alipayConfig.setPrivateKey(privateKey);
//        alipayConfig.setFormat(format);
//        alipayConfig.setCharset(charset);
//        alipayConfig.setAlipayPublicKey(publicKey);
//        alipayConfig.setSignType(signType);
//        //构造client
//        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
//
//        return alipayClient;
//    }
//
//
//    public AlipayClient getAlipayClient() {
//        if (null == alipayClient) {
//            try {
//                return aliPayClient();
//            } catch (AlipayApiException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            return alipayClient;
//        }
//    }
//
//}
