package com.zhu.pay_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName indexController.java
 * @Description TODO
 * @createTime 2021年09月12日 16:02:00
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String toTest(){
        return "index";
    }

    @RequestMapping("/notifyUrl")
    public String toNotify_url(){
        return "notify_url";
    }

    @RequestMapping("/returnUrl")
    public String toReturn_url(){
        return "return_url";
    }

    @RequestMapping("/face/pay")
    public String to_face_pay(){
        return "facePay";
    }

}
