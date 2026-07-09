package com.renthub.payment.service;

public interface RazorpayVerificationService {

    boolean verifySignature(
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature);

}