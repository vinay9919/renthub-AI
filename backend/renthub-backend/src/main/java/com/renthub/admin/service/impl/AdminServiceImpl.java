package com.renthub.admin.service.impl;

import com.renthub.admin.dto.DashboardResponse;
import com.renthub.admin.service.AdminService;
import com.renthub.booking.repository.BookingRepository;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.listing.dto.ListingResponse;
import com.renthub.listing.entity.Listing;
import com.renthub.listing.mapper.ListingMapper;
import com.renthub.listing.model.ListingStatus;
import com.renthub.listing.repository.CategoryRepository;
import com.renthub.listing.repository.ListingRepository;
import com.renthub.payment.entity.Payment;
import com.renthub.payment.model.PaymentStatus;
import com.renthub.payment.repository.PaymentRepository;
import com.renthub.review.repository.ReviewRepository;
import com.renthub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.renthub.admin.dto.AdminUserResponse;
import com.renthub.user.entity.User;
import com.renthub.user.model.UserStatus;
import com.renthub.booking.dto.BookingResponse;
import com.renthub.booking.mapper.BookingMapper;
import com.renthub.admin.dto.AdminPaymentResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public DashboardResponse getDashboard() {

        double revenue = paymentRepository.findAll()
                .stream()
                .filter(payment ->
                        payment.getStatus() == PaymentStatus.SUCCESS)
                .map(Payment::getAmount)
                .mapToDouble(java.math.BigDecimal::doubleValue)
                .sum();

        return DashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalListings(listingRepository.count())
                .totalBookings(bookingRepository.count())
                .totalPayments(paymentRepository.count())
                .totalReviews(reviewRepository.count())
                .totalCategories(categoryRepository.count())
                .totalRevenue(revenue)
                .build();
    }
    @Override
public List<AdminUserResponse> getAllUsers() {

    return userRepository.findAll()
            .stream()
            .map(user -> AdminUserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .role(user.getRole().name())
                    .status(user.getStatus().name())
                    .emailVerified(user.isEmailVerified())
                    .phoneVerified(user.isPhoneVerified())
                    .build())
            .toList();
}
@Override
public AdminUserResponse getUser(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    return AdminUserResponse.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole().name())
            .status(user.getStatus().name())
            .emailVerified(user.isEmailVerified())
            .phoneVerified(user.isPhoneVerified())
            .build();
}

@Override
public void blockUser(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    user.setStatus(UserStatus.BLOCKED);

    userRepository.save(user);
}

@Override
public void unblockUser(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    user.setStatus(UserStatus.ACTIVE);

    userRepository.save(user);
}

@Override
public void deleteUser(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    user.setStatus(UserStatus.INACTIVE);

    userRepository.save(user);
}
@Override
public List<ListingResponse> getAllListings() {

    return listingRepository.findAll()
            .stream()
            .map(ListingMapper::toResponse)
            .toList();
}

@Override
public void approveListing(Long listingId) {

    Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Listing not found"));

    listing.setStatus(ListingStatus.ACTIVE);

    listingRepository.save(listing);
}

@Override
public void rejectListing(Long listingId) {

    Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Listing not found"));

    listing.setStatus(ListingStatus.INACTIVE);

    listingRepository.save(listing);
}

@Override
public void deleteListing(Long listingId) {

    Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Listing not found"));

    listing.setStatus(ListingStatus.INACTIVE);

    listingRepository.save(listing);
}
@Override
public List<BookingResponse> getAllBookings() {

    return bookingRepository.findAll()
            .stream()
            .map(BookingMapper::toResponse)
            .toList();
}
@Override
public List<AdminPaymentResponse> getAllPayments() {

    return paymentRepository.findAllWithBookingAndCustomer()
            .stream()
            .map(payment -> {

                System.out.println("Payment ID: " + payment.getId());

                System.out.println("Booking ID");
                Long bookingId = payment.getBooking().getId();

                System.out.println("Customer Name");
                String customer = payment.getBooking().getCustomer().getFullName();

                System.out.println("Amount");
                var amount = payment.getAmount();

                System.out.println("Building DTO");

                return AdminPaymentResponse.builder()
                        .id(payment.getId())
                        .bookingId(bookingId)
                        .customerName(customer)
                        .amount(amount)
                        .status(payment.getStatus())
                        .gateway(payment.getGateway())
                        .transactionId(payment.getTransactionId())
                        .razorpayOrderId(payment.getRazorpayOrderId())
                        .createdAt(payment.getCreatedAt())
                        .build();
            })
            .toList();
}
}