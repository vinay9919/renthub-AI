package com.renthub.kyc.controller;

import com.renthub.kyc.dto.KycResponse;
import com.renthub.kyc.dto.UploadKycRequest;
import com.renthub.kyc.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public KycResponse uploadKyc(
            @ModelAttribute UploadKycRequest request) {

        return kycService.uploadKyc(request);
    }

    @GetMapping("/status")
    public KycResponse getStatus() {

        return kycService.getCurrentUserKyc();
    }

}