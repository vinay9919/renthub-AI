package com.renthub.kyc.mapper;

import com.renthub.kyc.dto.KycResponse;
import com.renthub.kyc.entity.Kyc;

public class KycMapper {

    private KycMapper() {
    }

    public static KycResponse toResponse(Kyc kyc) {

        KycResponse response = new KycResponse();

        response.setId(kyc.getId());

        response.setUserId(kyc.getUser().getId());

        response.setAadhaarNumber(kyc.getAadhaarNumber());

        response.setPanNumber(kyc.getPanNumber());

        response.setAadhaarFrontUrl(kyc.getAadhaarFrontUrl());

        response.setAadhaarBackUrl(kyc.getAadhaarBackUrl());

        response.setPanCardUrl(kyc.getPanCardUrl());

        response.setSelfieUrl(kyc.getSelfieUrl());

        response.setAddressProofUrl(kyc.getAddressProofUrl());

        response.setStatus(kyc.getStatus());

        response.setRejectionReason(kyc.getRejectionReason());

        response.setVerifiedBy(kyc.getVerifiedBy());

        response.setVerifiedAt(kyc.getVerifiedAt());

        return response;
    }

}