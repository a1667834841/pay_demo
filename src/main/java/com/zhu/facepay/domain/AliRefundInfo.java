package com.zhu.facepay.domain;

import lombok.Data;

@Data
public class AliRefundInfo {

    /**
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 退款金额
     */
    private String refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款请求号
     */
    private String outRequestNo;


}
