package com.zhu.facepay.service.impl;

import com.alipay.api.AlipayApiException;
import com.zhu.facepay.utils.PayUtils;
import com.zhu.facepay.domain.AliPayInfo;
import com.zhu.facepay.service.PayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName AliPayService.java
 * @Description TODO
 * @createTime 2022年11月21日 18:51:00
 */
@Service
public class AliPayService implements PayService {

    @Resource
    PayUtils payUtils;

    @Override
    public String qrCode(String totalAmount, String subject,String outTradeNo) {
        AliPayInfo aliPayInfo = new AliPayInfo();
        aliPayInfo.setOutTradeNo(outTradeNo);
        aliPayInfo.setSubject(subject);
        aliPayInfo.setTotalAmount(totalAmount);
        try {
            return payUtils.preCreateOrder(aliPayInfo);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
