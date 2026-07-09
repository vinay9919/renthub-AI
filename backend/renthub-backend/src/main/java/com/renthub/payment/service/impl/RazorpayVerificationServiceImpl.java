package com.renthub.payment.service.impl;

import com.renthub.payment.service.RazorpayVerificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class RazorpayVerificationServiceImpl
        implements RazorpayVerificationService {

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @Override
    public boolean verifySignature(
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature) {

        try {

            String payload =
                    razorpayOrderId + "|" + razorpayPaymentId;

            Mac sha256 =
                    Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKey =
                    new SecretKeySpec(
                            razorpaySecret.getBytes(),
                            "HmacSHA256");

            sha256.init(secretKey);

            byte[] hash =
                    sha256.doFinal(payload.getBytes());

            String generatedSignature =
                    Base64.getEncoder()
                            .encodeToString(hash);

            return generatedSignature.equals(razorpaySignature);

        } catch (Exception ex) {

            return false;

        }

    }
}