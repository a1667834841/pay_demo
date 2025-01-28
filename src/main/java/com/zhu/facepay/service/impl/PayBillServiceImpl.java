package com.zhu.facepay.service.impl;

import com.zhu.facepay.dao.PayBillRepository;
import com.zhu.facepay.domain.PayBill;
import com.zhu.facepay.domain.res.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName PayBillServiceimpl.java
 * @Description TODO
 * @createTime 2022年11月22日 11:42:00
 */
@Service("payBillService")
public class PayBillServiceImpl {

    @Autowired
    private PayBillRepository payBillRepository;


    public List<PayBill> all() {
        return payBillRepository.findAll();
    }

    /**
     * getPayBillById
     * @param id
     * @return
     */
    public PayBill getPayBillById(Long id) {
        return payBillRepository.findById(id).get();
    }

    public void updatePayBillByOrderNum(PayBill payBill) {
        payBillRepository.updatePayBillByOrderNum(payBill);
    }

    public synchronized PayBill updatePayBill(PayBill payBill) {
        PayBill oldPayBill = payBillRepository.getPayBillByOrderNum(payBill.getOrderNum());
        oldPayBill.setIsPay(payBill.getIsPay());
        oldPayBill.setSellerEmail(payBill.getSellerEmail());
        oldPayBill.setNotifyId(payBill.getNotifyId());
        oldPayBill.setSellerId(payBill.getSellerId());
        oldPayBill.setTradeNo(payBill.getTradeNo());
        oldPayBill.setUpdateTime(new Date());
        oldPayBill.setPayerId(payBill.getPayerId());
        oldPayBill.setBuyerLogonId(payBill.getBuyerLogonId());

        return payBillRepository.saveAndFlush(oldPayBill);
    }

    public synchronized PayBill save(PayBill payBill){
        payBill.setCreateTime(new Date());
        payBill.setUpdateTime(new Date());
        payBill.setIsPay(false);
        return payBillRepository.save(payBill);
    }

    public ResultData<PayBill> getPayBillByOrderNum(String orderNum){
        PayBill payBillByOrderNum = payBillRepository.getPayBillByOrderNum(orderNum);
        if (null == payBillByOrderNum) {
            return ResultData.fail(500,"未找到");
        }
        return ResultData.success(payBillByOrderNum) ;
    }

    public PayBill getPayBillByNotifyId(String notifyId) {
        return payBillRepository.getPayBillByNotifyId(notifyId);
    }

    public List<PayBill> latest(Integer num) {

        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        //分页:第一条到第十条
        PageRequest pagerequest = PageRequest.of(0,10,sort);
        return  payBillRepository.findAll(pagerequest).getContent();
    }
}
