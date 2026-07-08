package com.renthub.kyc.service.impl;

import com.renthub.cloudinary.service.CloudinaryService;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.kyc.dto.KycResponse;
import com.renthub.kyc.dto.UploadKycRequest;
import com.renthub.kyc.entity.Kyc;
import com.renthub.kyc.mapper.KycMapper;
import com.renthub.kyc.model.KycStatus;
import com.renthub.kyc.repository.KycRepository;
import com.renthub.kyc.service.KycService;
import com.renthub.security.service.AuthenticatedUserService;
import com.renthub.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;
    private final CloudinaryService cloudinaryService;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public KycResponse uploadKyc(UploadKycRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        Kyc kyc = kycRepository.findByUser(user)
                .orElse(new Kyc());

        if (kyc.getId() == null) {
            kyc.setUser(user);
        }

        if (kycRepository.existsByAadhaarNumber(request.getAadhaarNumber())
                && (kyc.getAadhaarNumber() == null
                || !kyc.getAadhaarNumber().equals(request.getAadhaarNumber()))) {

            throw new IllegalArgumentException("Aadhaar already exists");
        }

        if (kycRepository.existsByPanNumber(request.getPanNumber())
                && (kyc.getPanNumber() == null
                || !kyc.getPanNumber().equals(request.getPanNumber()))) {

            throw new IllegalArgumentException("PAN already exists");
        }

        kyc.setAadhaarNumber(request.getAadhaarNumber());
        kyc.setPanNumber(request.getPanNumber());

        if (request.getAadhaarFront() != null && !request.getAadhaarFront().isEmpty()) {
            kyc.setAadhaarFrontUrl(
                    cloudinaryService.uploadImage(request.getAadhaarFront()));
        }

        if (request.getAadhaarBack() != null && !request.getAadhaarBack().isEmpty()) {
            kyc.setAadhaarBackUrl(
                    cloudinaryService.uploadImage(request.getAadhaarBack()));
        }

        if (request.getPanCard() != null && !request.getPanCard().isEmpty()) {
            kyc.setPanCardUrl(
                    cloudinaryService.uploadImage(request.getPanCard()));
        }

        if (request.getSelfie() != null && !request.getSelfie().isEmpty()) {
            kyc.setSelfieUrl(
                    cloudinaryService.uploadImage(request.getSelfie()));
        }

        if (request.getAddressProof() != null && !request.getAddressProof().isEmpty()) {
            kyc.setAddressProofUrl(
                    cloudinaryService.uploadImage(request.getAddressProof()));
        }

        kyc.setStatus(KycStatus.UNDER_REVIEW);

        return KycMapper.toResponse(
                kycRepository.save(kyc));
    }

    @Override
    @Transactional(readOnly = true)
    public KycResponse getCurrentUserKyc() {

        User user = authenticatedUserService.getCurrentUser();

        Kyc kyc = kycRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("KYC not found"));

        return KycMapper.toResponse(kyc);
    }

}