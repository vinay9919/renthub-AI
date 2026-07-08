package com.renthub.kyc.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadKycRequest {

    private String aadhaarNumber;

    private String panNumber;

    private MultipartFile aadhaarFront;

    private MultipartFile aadhaarBack;

    private MultipartFile panCard;

    private MultipartFile selfie;

    private MultipartFile addressProof;

}