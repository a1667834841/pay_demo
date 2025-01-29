package com.zhu.facepay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.zhu.facepay.config.AliPayBusinessConfig;
import com.zhu.facepay.domain.AliPayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Http2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName payUtils.java
 * @Description TODO
 * @createTime 2022年11月21日 10:56:00
 */
@Slf4j
@Component("payUtils")
public class PayUtils {

    @Resource
    private AliPayBusinessConfig aliPayBusinessConfig;

    /**
     * 正式环境
     */
    private static final String NOTIFY_PROD_URL = "https://ggball.top/notify/ali/pay/face/notify";

    /**
     * 测试环境 需要先开启远程穿透
     */
    private static final String NOTIFY_test_URL = "http://1.15.141.114:9000/ali/pay/face/notify";


    /**
     * 支付回调路径
     */
    private static final String NOTIFY_PATH = "/notify/ali/pay/face/notify";

    /**
     * 预下单
     * @param aliPayInfo
     * @return
     * @throws AlipayApiException
     */
    public String preCreateOrder(AliPayInfo aliPayInfo) throws AlipayApiException {
        AlipayClient alipayClient = aliPayBusinessConfig.getAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(Ip.getPublicIpv4()+NOTIFY_PATH);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", aliPayInfo.getOutTradeNo());
        bizContent.put("total_amount", aliPayInfo.getTotalAmount());
        bizContent.put("subject", aliPayInfo.getSubject());

//// 商品明细信息，按需传入
//JSONArray goodsDetail = new JSONArray();
//JSONObject goods1 = new JSONObject();
//goods1.put("goods_id", "goodsNo1");
//goods1.put("goods_name", "子商品1");
//goods1.put("quantity", 1);
//goods1.put("price", 0.01);
//goodsDetail.add(goods1);
//bizContent.put("goods_detail", goodsDetail);

//// 扩展信息，按需传入
//JSONObject extendParams = new JSONObject();
//extendParams.put("sys_service_provider_id", "2088511833207846");
//bizContent.put("extend_params", extendParams);

//// 结算信息，按需传入
//JSONObject settleInfo = new JSONObject();
//JSONArray settleDetailInfos = new JSONArray();
//JSONObject settleDetail = new JSONObject();
//settleDetail.put("trans_in_type", "defaultSettle");
//settleDetail.put("amount", 0.01);
//settleDetailInfos.add(settleDetail);
//settleInfo.put("settle_detail_infos", settleDetailInfos);
//bizContent.put("settle_info", settleInfo);

//// 二级商户信息，按需传入
//JSONObject subMerchant = new JSONObject();
//subMerchant.put("merchant_id", "2088000603999128");
//bizContent.put("sub_merchant", subMerchant);

//// 业务参数信息，按需传入
//JSONObject businessParams = new JSONObject();
//businessParams.put("busi_params_key", "busiParamsValue");
//bizContent.put("business_params", businessParams);

//// 营销信息，按需传入
//JSONObject promoParams = new JSONObject();
//promoParams.put("promo_params_key", "promoParamsValue");
//bizContent.put("promo_params", promoParams);

        request.setBizContent(bizContent.toString());
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            log.info("res:{}",response.getBody());

            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            return jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");

        } else {
            log.error("res:{}",response.getBody());
            System.out.println("调用失败");
            return "";
        }
    }
}
