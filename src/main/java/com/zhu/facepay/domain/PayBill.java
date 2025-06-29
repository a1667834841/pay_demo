package com.zhu.facepay.domain;


import com.zhu.facepay.utils.Node;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName Order.java
 * @Description TODO
 * @createTime 2022年11月22日 11:26:00
 */


@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "PayBill")
public class PayBill extends Node implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 商家订单号
     */
    private String orderNum;

    /**
     * 支付宝订单号
     */
    private String tradeNo;

    /**
     * 买家登录id
     */
    private String buyerLogonId;

    /**
     * 卖家邮箱
     */
    private String sellerEmail;


    /**
     * 支付回调id 用幂等判断
     */
    private String notifyId;


    /**
     * 支付人
     */
    private String payerId;

    /**
     * 收款人
     */
    private String sellerId;


    /**
     * 订单金额
     */
    private String payAmount;

    /**
     * 是否付款
     */
    private Boolean isPay;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;


    /**
     * 调用方填的回调地址
     */
    private String notifyUrl;

    /**
     * 是否退款
     */
    private Boolean isRefund;


}
