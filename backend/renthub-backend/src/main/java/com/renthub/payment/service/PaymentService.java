package com.renthub.payment.service;

import java.util.List;

import com.renthub.payment.dto.CreatePaymentRequest;
import com.renthub.payment.dto.PaymentResponse;
import com.renthub.payment.dto.PaymentVerificationRequest;

public interface PaymentService {

    PaymentResponse createOrder(CreatePaymentRequest request) throws Exception;

   PaymentResponse verifyPayment(
        PaymentVerificationRequest request);

List<PaymentResponse> getMyPayments();

PaymentResponse refund(Long paymentId);

void processWebhook(String payload);
}