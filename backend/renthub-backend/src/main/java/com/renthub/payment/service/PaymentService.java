package com.renthub.payment.service;

import com.renthub.payment.dto.CreatePaymentRequest;
import com.renthub.payment.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createOrder(CreatePaymentRequest request) throws Exception;

}