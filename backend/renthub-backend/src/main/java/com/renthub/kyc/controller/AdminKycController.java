package com.renthub.kyc.controller;

import com.renthub.kyc.dto.KycResponse;
import com.renthub.kyc.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/kyc")
@RequiredArgsConstructor
public class AdminKycController {

    private final KycService kycService;

    @GetMapping
    public List<KycResponse> pending() {
        return kycService.getPendingKycs();
    }

    @PutMapping("/{id}/approve")
    public KycResponse approve(@PathVariable Long id) {
        return kycService.approve(id);
    }

    @PutMapping("/{id}/reject")
    public KycResponse reject(
            @PathVariable Long id,
            @RequestParam String reason) {

        return kycService.reject(id, reason);
    }
}