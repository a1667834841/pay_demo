package com.zhu.facepay.domain.dto;

import lombok.Data;

@Data
public class RefundReq {

    /**
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outTradeNo;


}
