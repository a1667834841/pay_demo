package com.zhu.facepay.controller;

import com.zhu.facepay.domain.PayBill;
import com.zhu.facepay.domain.res.ResultData;
import com.zhu.facepay.service.impl.PayBillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName PayBillController.java
 * @Description TODO
 * @createTime 2022年11月22日 11:45:00
 */
@RestController
@RequestMapping("/payBill")
public class PayBillController {

    @Autowired
    private PayBillServiceImpl payBillService;

    @GetMapping("/init")
    public String init(){
        PayBill payBill = null;
        for(int i=0;i<10;i++){
            payBill = new PayBill();
            payBill.setOrderNum("test"+i);
            payBillService.save(payBill);
        }
        return "初始化完成。";
    }

    @GetMapping("/all")
    public List<PayBill> all(){
        return payBillService.all();
    }

    @GetMapping("/latest/{num}")
    public ResultData<List<PayBill>> latest(@PathVariable("num") Integer num){
        return ResultData.success(payBillService.latest(num));
    }

    @GetMapping("/page")
    public ResultData<Map<String, Object>> getPayBillsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String orderNum,
            @RequestParam(required = false) String buyerLogonId,
            @RequestParam(required = false) Boolean isPay) {
        return ResultData.success(payBillService.getPayBillsWithPagination(page, size, orderNum, buyerLogonId, isPay));
    }

    @GetMapping("/payBillByOrderNum/{orderNum}")
    public ResultData<PayBill> getPayBillByName(@PathVariable("orderNum") String orderNum){
        return payBillService.getPayBillByOrderNum(orderNum);
    }

    @PostMapping("/post")
    public PayBill getUserByName(@RequestBody PayBill payBill){
        return payBillService.save(payBill);
    }



}
