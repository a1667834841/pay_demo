package com.zhu.facepay.domain.out;

import lombok.Data;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName PayNotifyVO.java
 * @Description 支付响应结果
 * @createTime 2022年11月23日 20:48:00
 */
@Data
public class PayNotifyVO {

    /**
     * 商家订单号
     */
    private String orderNum;

    /**
     * 支付回调id 用幂等判断
     */
    private String notifyId;

    /**
     * 订单金额
     */
    private String payAmount;


    /**
     * 交易结果 success，fail
     */
    private String tradeResult;
}
