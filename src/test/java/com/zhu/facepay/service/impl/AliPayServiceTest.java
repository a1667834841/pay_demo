package com.zhu.facepay.service.impl;

import com.alipay.api.AlipayApiException;
import com.zhu.facepay.domain.AliPayInfo;
import com.zhu.facepay.utils.PayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AliPayServiceTest {

    @Mock
    private PayUtils payUtils;

    @InjectMocks
    private AliPayService aliPayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void qrCode_shouldCreateOrderSuccessfully() throws AlipayApiException {
        // Arrange
        String totalAmount = "100.00";
        String subject = "Test Product";
        String outTradeNo = "ORDER123456";
        String expectedQrCode = "https://qr.alipay.com/test-qr-code";
        
        when(payUtils.preCreateOrder(any(AliPayInfo.class))).thenReturn(expectedQrCode);

        // Act
        String result = aliPayService.qrCode(totalAmount, subject, outTradeNo);

        // Assert
        assertEquals(expectedQrCode, result);
        
        // Verify that preCreateOrder was called with correct parameters
        verify(payUtils, times(1)).preCreateOrder(argThat(aliPayInfo -> 
            aliPayInfo.getTotalAmount().equals(totalAmount) &&
            aliPayInfo.getSubject().equals(subject) &&
            aliPayInfo.getOutTradeNo().equals(outTradeNo)
        ));
    }

    @Test
    void qrCode_shouldThrowRuntimeException_whenAlipayApiExceptionOccurs() throws AlipayApiException {
        // Arrange
        String totalAmount = "100.00";
        String subject = "Test Product";
        String outTradeNo = "ORDER123456";
        
        when(payUtils.preCreateOrder(any(AliPayInfo.class))).thenThrow(new AlipayApiException("API Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            aliPayService.qrCode(totalAmount, subject, outTradeNo)
        );
        
        // Verify exception has AlipayApiException as cause
        assertEquals(AlipayApiException.class, exception.getCause().getClass());
    }
}