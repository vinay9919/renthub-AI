package com.renthub.admin.service.impl;

import org.springframework.stereotype.Service;

import com.renthub.admin.dto.DashboardResponse;
import com.renthub.admin.service.AdminService;
import com.renthub.booking.repository.BookingRepository;
import com.renthub.kyc.model.KycStatus;
import com.renthub.kyc.repository.KycRepository;
import com.renthub.listing.repository.ListingRepository;
import com.renthub.payment.repository.PaymentRepository;
import com.renthub.review.repository.ReviewRepository;
import com.renthub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
     
private final UserRepository userRepository;
private final ListingRepository listingRepository;
private final BookingRepository bookingRepository;
private final PaymentRepository paymentRepository;
private final ReviewRepository reviewRepository;
private final KycRepository kycRepository;

@Override
public DashboardResponse getDashboard() {

    return DashboardResponse.builder()
            .totalUsers(userRepository.count())
            .totalListings(listingRepository.count())
            .totalBookings(bookingRepository.count())
            .totalPayments(paymentRepository.count())
            .totalReviews(reviewRepository.count())
            .pendingKyc(
                    kycRepository.countByStatus(
                            KycStatus.PENDING))
            .build();
}

}
