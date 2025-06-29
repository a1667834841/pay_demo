package com.zhu.facepay.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.zhu.facepay.domain.AliRefundInfo;
import com.zhu.facepay.domain.PayBill;
import com.zhu.facepay.domain.dto.RefundReq;
import com.zhu.facepay.domain.dto.RefundRes;
import com.zhu.facepay.domain.res.ResultData;
import com.zhu.facepay.repository.PayBillRepository;
import com.zhu.facepay.utils.PayUtils;
import com.zhu.facepay.domain.AliPayInfo;
import com.zhu.facepay.service.PayService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AliPayService implements PayService {

    @Resource
    PayUtils payUtils;
    @Resource
    PayBillRepository payBillRepository;

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

    @Override
    public ResultData<RefundRes> refund(RefundReq refundReq) throws AlipayApiException {

        // 查询订单
        PayBill payBill = payBillRepository.getPayBillByOrderNum(refundReq.getOutTradeNo());
        if (payBill == null) {
            return ResultData.fail("订单不存在");
        }

        AliRefundInfo aliRefundInfo = new AliRefundInfo();
        aliRefundInfo.setOutTradeNo(refundReq.getOutTradeNo());
        aliRefundInfo.setTradeNo(refundReq.getTradeNo());
        aliRefundInfo.setOutRequestNo("RF" + DateUtil.current()+ UUID.fastUUID());
        aliRefundInfo.setRefundAmount(payBill.getPayAmount());

        AlipayTradeRefundResponse refundResponse = payUtils.refund(aliRefundInfo);
        if (refundResponse.isSuccess() && "Y".equals(refundResponse.getFundChange())) {

            // 修改订单退款状态
            payBill.setIsRefund(false);
            payBillRepository.saveAndFlush(payBill);
            log.info("订单号：{} 退款成功", refundReq.getOutTradeNo());


            RefundRes refundRes = new RefundRes();
            refundRes.setOutTradeNo(refundReq.getOutTradeNo());
            refundRes.setTradeNo(refundResponse.getTradeNo());
            refundRes.setRefundFee(refundResponse.getRefundFee());
            return ResultData.success(refundRes);
        }
        return ResultData.fail("退款失败: " + refundResponse.getSubMsg());
    }
}
