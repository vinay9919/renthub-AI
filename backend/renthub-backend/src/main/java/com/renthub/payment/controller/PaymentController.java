package com.renthub.payment.controller;

import com.renthub.payment.dto.CreatePaymentRequest;
import com.renthub.payment.dto.PaymentResponse;
import com.renthub.payment.dto.PaymentVerificationRequest;
import com.renthub.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create-order")
    public PaymentResponse createOrder(
            @Valid @RequestBody CreatePaymentRequest request) throws Exception {
        System.out.println(">>> Payment Controller Reached");
        return paymentService.createOrder(request);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/verify")
public PaymentResponse verifyPayment(
        @RequestBody PaymentVerificationRequest request) {

    return paymentService.verifyPayment(request);

}
}