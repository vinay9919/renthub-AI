package com.renthub.payment.service.impl;

import com.renthub.booking.entity.Booking;
import com.renthub.booking.model.BookingStatus;
import com.renthub.booking.repository.BookingRepository;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.payment.dto.CreatePaymentRequest;
import com.renthub.payment.dto.PaymentResponse;
import com.renthub.payment.entity.Payment;
import com.renthub.payment.mapper.PaymentMapper;
import com.renthub.payment.model.PaymentStatus;
import com.renthub.payment.repository.PaymentRepository;
import com.renthub.payment.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.renthub.payment.dto.PaymentVerificationRequest;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final RazorpayClient razorpayClient;

    @Override
@Transactional
public PaymentResponse createOrder(CreatePaymentRequest request) throws Exception {

    Booking booking = bookingRepository.findById(request.getBookingId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Booking not found"));

    // Prevent duplicate payment
    if (paymentRepository.findByBookingId(booking.getId()).isPresent()) {
        throw new IllegalArgumentException(
                "Payment already exists for this booking");
    }

    // Prevent owner renting own listing
    if (booking.getCustomer().getId()
            .equals(booking.getListing().getOwner().getId())) {

        throw new IllegalArgumentException(
                "You cannot rent your own listing");
    }

    // Booking must be pending
    if (booking.getStatus() != BookingStatus.APPROVED) {
    throw new IllegalArgumentException(
            "Only approved bookings can be paid");
}

    BigDecimal amount = booking.getListing().getPrice();

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException(
                "Invalid booking amount");
    }

    JSONObject options = new JSONObject();

    options.put("amount",
            amount.multiply(BigDecimal.valueOf(100)));

    options.put("currency", "INR");

    options.put("receipt",
            "booking_" + booking.getId());

    Order order = razorpayClient.orders.create(options);

    Payment payment = new Payment();

    payment.setBooking(booking);

    payment.setAmount(amount);

    payment.setStatus(PaymentStatus.CREATED);

    payment.setGateway("RAZORPAY");

    payment.setTransactionId(
            java.util.UUID.randomUUID().toString());

    payment.setRazorpayOrderId(
            order.get("id").toString());

    Payment saved = paymentRepository.save(payment);

    return PaymentMapper.toResponse(saved);
}

@Override
@Transactional
public PaymentResponse verifyPayment(
        PaymentVerificationRequest request) {

    if (request.getRazorpayOrderId() == null ||
        request.getRazorpayPaymentId() == null ||
        request.getRazorpaySignature() == null) {

        throw new IllegalArgumentException(
                "Invalid payment verification request");
    }

    Payment payment = paymentRepository
            .findByRazorpayOrderId(request.getRazorpayOrderId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Payment not found"));

    if (payment.getStatus() == PaymentStatus.SUCCESS) {
        throw new IllegalArgumentException(
                "Payment is already verified");
    }

    payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
    payment.setRazorpaySignature(request.getRazorpaySignature());
    payment.setStatus(PaymentStatus.SUCCESS);
    payment.setPaidAt(java.time.LocalDateTime.now());
    payment.setUpdatedAt(java.time.LocalDateTime.now());

    Booking booking = payment.getBooking();
    booking.setStatus(
            com.renthub.booking.model.BookingStatus.APPROVED);

    bookingRepository.save(booking);
    paymentRepository.save(payment);

    return PaymentMapper.toResponse(payment);
}

    @Override
    public List<PaymentResponse> getMyPayments() {
        throw new UnsupportedOperationException("Will implement in Sprint 18 - Step 3");
    }

    @Override
    public PaymentResponse refund(Long paymentId) {
        throw new UnsupportedOperationException("Will implement in Sprint 18 - Step 4");
    }

    @Override
    public void processWebhook(String payload) {
        throw new UnsupportedOperationException("Will implement in Sprint 18 - Step 5");
    }
}