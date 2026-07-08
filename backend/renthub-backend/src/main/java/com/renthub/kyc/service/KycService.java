package com.renthub.kyc.service;

import com.renthub.kyc.dto.KycResponse;
import com.renthub.kyc.dto.UploadKycRequest;

public interface KycService {

    KycResponse uploadKyc(UploadKycRequest request);

    KycResponse getCurrentUserKyc();

}