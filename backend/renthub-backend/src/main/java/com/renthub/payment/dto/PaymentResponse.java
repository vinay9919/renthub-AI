package com.renthub.payment.dto;

import com.renthub.payment.model.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PaymentResponse {

    private Long id;

    private Long bookingId;

    private BigDecimal amount;

    private PaymentStatus status;

    private String razorpayOrderId;

    private String razorpayPaymentId;
    
    private String transactionId;

    private String paymentMethod;

    private String gateway;

    private String refundStatus;

}