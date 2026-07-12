package com.renthub.kyc.entity;

import com.renthub.common.entity.BaseEntity;
import com.renthub.kyc.model.KycStatus;
import com.renthub.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc")
@Getter
@Setter
public class Kyc extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String aadhaarNumber;

    private String panNumber;

    private String aadhaarFrontUrl;

    private String aadhaarBackUrl;

    private String panCardUrl;

    private String selfieUrl;

    private String addressProofUrl;

    @Enumerated(EnumType.STRING)
    private KycStatus status = KycStatus.PENDING;

    private String rejectionReason;

    private Long verifiedBy;

    private LocalDateTime verifiedAt;

    private Integer resubmissionCount = 0;

}