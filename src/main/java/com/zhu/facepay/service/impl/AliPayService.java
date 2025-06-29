package com.zhu.facepay.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.zhu.facepay.domain.AliRefundInfo;
import com.zhu.facepay.domain.dto.RefundReq;
import com.zhu.facepay.domain.dto.RefundRes;
import com.zhu.facepay.domain.res.ResultData;
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

    @Override
    public ResultData<RefundRes> refund(RefundReq refundReq) throws AlipayApiException {

        AliRefundInfo aliRefundInfo = new AliRefundInfo();
        aliRefundInfo.setOutTradeNo(refundReq.getOutTradeNo());
        aliRefundInfo.setTradeNo(refundReq.getTradeNo());
        aliRefundInfo.setOutRequestNo("RF" + DateUtil.current()+ UUID.fastUUID());

        AlipayTradeRefundResponse refundResponse = payUtils.refund(aliRefundInfo);
        if (refundResponse.isSuccess() && "Y".equals(refundResponse.getFundChange())) {
            RefundRes refundRes = new RefundRes();
            refundRes.setOutTradeNo(refundReq.getOutTradeNo());
            refundRes.setTradeNo(refundResponse.getTradeNo());
            refundRes.setRefundFee(refundResponse.getRefundFee());
            return ResultData.success(refundRes);
        }
        return ResultData.fail("退款失败: " + refundResponse.getSubMsg());
    }
}
