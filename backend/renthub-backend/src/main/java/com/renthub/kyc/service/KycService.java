package com.renthub.kyc.service;

import java.util.List;

import com.renthub.kyc.dto.KycResponse;
import com.renthub.kyc.dto.UploadKycRequest;

public interface KycService {

    KycResponse uploadKyc(UploadKycRequest request);

    KycResponse getCurrentUserKyc();

    List<KycResponse> getPendingKycs();

KycResponse approve(Long id);

KycResponse reject(Long id, String reason);

}