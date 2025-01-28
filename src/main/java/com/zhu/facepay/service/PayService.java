package com.zhu.facepay.service;

public interface PayService {

    /**
     * 返回扫码支付需要的二维码
     * @param totalAmount
     * @param subject
     * @return
     */
    String qrCode(String totalAmount,String subject,String outTradeNo);
}
