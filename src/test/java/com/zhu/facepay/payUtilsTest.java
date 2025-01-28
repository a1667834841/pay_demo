package com.zhu.facepay;

import cn.hutool.core.date.DateUtil;
import com.alipay.api.AlipayApiException;
import com.zhu.facepay.domain.AliPayInfo;
import com.zhu.PayDemoApplication;
import com.zhu.facepay.utils.PayUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes= PayDemoApplication.class)
class payUtilsTest {

    @Resource
    PayUtils payUtils;

    @Test
    void client() {
    }

    @Test
    void preCreateOrder() throws AlipayApiException {
        AliPayInfo aliPayInfo = new AliPayInfo();
        aliPayInfo.setOutTradeNo("JY"+DateUtil.current());
        aliPayInfo.setSubject("测试");
        aliPayInfo.setTotalAmount("0.01");
        payUtils.preCreateOrder(aliPayInfo);
    }
}