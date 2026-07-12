package com.renthub.admin.dto;

import com.renthub.payment.model.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AdminPaymentResponse {

    private Long id;

    private Long bookingId;

    private String customerName;

    private BigDecimal amount;

    private PaymentStatus status;

    private String gateway;

    private String transactionId;

    private String razorpayOrderId;

    private LocalDateTime createdAt;
}