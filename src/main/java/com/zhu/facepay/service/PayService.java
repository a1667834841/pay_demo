package com.zhu.facepay.service;

import com.alipay.api.AlipayApiException;
import com.zhu.facepay.domain.dto.RefundReq;
import com.zhu.facepay.domain.dto.RefundRes;
import com.zhu.facepay.domain.res.ResultData;

public interface PayService {

    /**
     * 返回扫码支付需要的二维码
     * @param totalAmount
     * @param subject
     * @return
     */
    String qrCode(String totalAmount,String subject,String outTradeNo);

    ResultData<RefundRes> refund(RefundReq refundReq) throws AlipayApiException;
}
