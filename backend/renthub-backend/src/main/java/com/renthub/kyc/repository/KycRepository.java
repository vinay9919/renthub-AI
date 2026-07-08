package com.renthub.kyc.repository;

import com.renthub.kyc.entity.Kyc;
import com.renthub.kyc.model.KycStatus;
import com.renthub.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KycRepository extends JpaRepository<Kyc, Long> {

    Optional<Kyc> findByUser(User user);

    boolean existsByAadhaarNumber(String aadhaarNumber);

    boolean existsByPanNumber(String panNumber);

    List<Kyc> findByStatus(KycStatus status);

}