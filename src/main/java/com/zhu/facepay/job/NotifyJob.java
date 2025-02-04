package com.zhu.facepay.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.zhu.facepay.domain.PayBill;
import com.zhu.facepay.domain.out.PayNotifyVO;
import com.zhu.facepay.utils.PriorityBlockQueue;
import com.zhu.facepay.utils.QueueCore;
import com.zhu.facepay.utils.SseEmitterServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName NotofyJob.java
 * @Description TODO
 * @createTime 2022年11月23日 21:03:00
 */
@Slf4j
@Component
public class NotifyJob implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {


        new Thread(() -> {
            log.info("推送任务开启。。。。");

            PriorityBlockQueue<PayBill> billQueueCore = new QueueCore<>("NOTIFY_JOB").get("NOTIFY_JOB");

            PayBill payBill = null;
            while (true) {
                try {
                    payBill = billQueueCore.removeBlock();
                    if (payBill.getTimes() > 10) {
                        log.info("超出推送次数，结束推送：orderNum{}",payBill.getOrderNum());
                        continue;
                    }

                    log.info("开始推送，orderNum：{},推送地址：{}",payBill.getOrderNum(),payBill.getNotifyUrl());
                    HttpRequest request = HttpUtil.createRequest(Method.POST, payBill.getNotifyUrl());
                    request.header("Content-Type","application/x-www-form-urlencoded");
                    PayNotifyVO payNotifyVO = new PayNotifyVO();
                    BeanUtils.copyProperties(payBill,payNotifyVO);
                    payNotifyVO.setTradeResult("success");
                    request.form("orderNum",payNotifyVO.getOrderNum());
                    request.form("notifyId",payNotifyVO.getNotifyId());
                    request.form("payAmount",payNotifyVO.getPayAmount());
                    request.form("tradeResult",payNotifyVO.getTradeResult());
//                    request.body(JSONObject.toJSONString(payNotifyVO));
                    log.info("开始推送，推送信息：{}",payNotifyVO);
                    HttpResponse response = request.execute(false);
                    log.info("response:{}",response.body());
                    if ("success".equalsIgnoreCase(response.body())) {
                        log.info("支付响应成功，tradeNo：{},time:{}",payBill.getTradeNo(), DateUtil.now());
                        SseEmitterServer.sendMessage(payBill.getOrderNum(),"时间："+DateUtil.format(payBill.getUpdateTime(),"yyyy-MM-dd HH:mm:ss")+payBill.getBuyerLogonId()+"支付成功！");
                    } else {
                        log.info("支付响应失败，tradeNo：{},time:{}",payBill.getTradeNo(), DateUtil.now());
                        billQueueCore.push(payBill);
                    }
                } catch (Exception e) {
                    log.info("支付异常，node将重新加入，tradeNo：{},time:{}",payBill.getTradeNo(), DateUtil.now());
                    e.printStackTrace();
                    try {
                        billQueueCore.push(payBill);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }
        },"job").start();


    }
}
