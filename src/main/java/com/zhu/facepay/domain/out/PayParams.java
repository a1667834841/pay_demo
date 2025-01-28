package com.zhu.facepay.domain.out;

import lombok.Data;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName PayParams.java
 * @Description 支付参数
 * @createTime 2022年11月23日 20:51:00
 */
@Data
public class PayParams {


    /**
     * 商家订单号
     */
    private String orderNum;


    /**
     * 订单金额
     */
    private String payAmount;


    /**
     * 回调地址
     */
    private String notifyUrl;

}
