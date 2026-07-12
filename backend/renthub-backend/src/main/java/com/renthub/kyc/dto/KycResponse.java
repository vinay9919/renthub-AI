package com.renthub.kyc.dto;

import com.renthub.kyc.model.KycStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KycResponse {

    private Long id;

    private Long userId;

    private String aadhaarNumber;

    private String panNumber;

    private String aadhaarFrontUrl;

    private String aadhaarBackUrl;

    private String panCardUrl;

    private String selfieUrl;

    private String addressProofUrl;

    private KycStatus status;

    private String rejectionReason;

    private Long verifiedBy;

    private LocalDateTime verifiedAt;

}