package com.zhu.facepay.domain.dto;

import lombok.Data;

@Data
public class RefundRes {

    /**
     * 退款金额
     */
    private String refundAmount;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 退款时间
     */
    private String gmtRefundPay;

    /**
     * 退款金额
      */
    private String refundFee;

    /**
     * 退款单号
     */
    private String outRequestNo;

    /**
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outTradeNo;
}
