package com.zhu.facepay.controller;

import com.zhu.facepay.service.impl.PayBillServiceImpl;
import com.zhu.facepay.utils.SseEmitterServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName TestController.java
 * @Description TODO
 * @createTime 2022年11月24日 17:54:00
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private PayBillServiceImpl payBillService;

    @PostMapping("/notify")
    public String notifyTest(Map map) {
        log.info("map:{}",map);
        return "success";
    }

//    @GetMapping("/result")
//    public String result(String orderNum) {
//        PayBill payBillByOrderNum = payBillService.getPayBillByOrderNum(orderNum);
//        if (null == payBillByOrderNum) {
//            return "false";
//        } else {
//            return "success";
//        }
//    }

    @GetMapping(value = "/result")
    @ResponseBody
    public SseEmitter result(String orderNum) {
        return SseEmitterServer.connect(orderNum);
    }

    @GetMapping(value = "/close")
    @ResponseBody
    public void close(String orderNum) {
        SseEmitterServer.removeUser(orderNum);
    }

}
