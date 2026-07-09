package com.renthub.payment.service;

import java.util.List;

import com.renthub.payment.dto.CreatePaymentRequest;
import com.renthub.payment.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createOrder(CreatePaymentRequest request) throws Exception;

    PaymentResponse verifyPayment(
        String razorpayOrderId,
        String razorpayPaymentId,
        String razorpaySignature);

List<PaymentResponse> getMyPayments();

PaymentResponse refund(Long paymentId);

void processWebhook(String payload);
}