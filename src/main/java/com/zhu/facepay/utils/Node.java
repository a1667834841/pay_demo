package com.zhu.facepay.utils;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName Node.java
 * @Description 队列节点
 * @createTime 2022年11月24日 14:48:00
 */
@Data
public class Node {

    /**
     * 初始时间
     */
    private Date seedTime;

    /**
     * 增加次数
     */
    private Integer times;

    public Node() {
        times= 0;
        seedTime = new Date();
    }

    public void incr() {
        times++;
    }

    /**
     * 增加时间
     */
    public void incrTime() {
        seedTime = DateUtil.offsetSecond(seedTime,times * 15);
        times++;
    }
}
