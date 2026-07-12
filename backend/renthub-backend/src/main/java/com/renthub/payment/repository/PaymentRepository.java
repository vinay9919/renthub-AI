package com.renthub.payment.repository;

import com.renthub.payment.entity.Payment;
import com.renthub.payment.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBookingId(Long bookingId);

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);

    List<Payment> findByStatus(PaymentStatus status);

    @Query("""
        SELECT p
        FROM Payment p
        JOIN FETCH p.booking b
        JOIN FETCH b.customer
    """)
    List<Payment> findAllWithBookingAndCustomer();

}