package com.zhu.facepay.domain;

import lombok.Data;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName AliPayInfo.java
 * @Description TODO
 * @createTime 2022年11月21日 18:04:00
 */
@Data
public class AliPayInfo {

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 总金额
     */
    private String totalAmount;


    /**
     * 商品名称
     */
    private String subject;
}
