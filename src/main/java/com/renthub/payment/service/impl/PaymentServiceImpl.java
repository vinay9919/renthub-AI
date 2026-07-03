package com.renthub.payment.service.impl;

import com.renthub.booking.entity.Booking;
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

import java.math.BigDecimal;

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

        BigDecimal amount = booking.getListing().getPrice();

        JSONObject options = new JSONObject();
        options.put("amount", amount.multiply(BigDecimal.valueOf(100)));
        options.put("currency", "INR");
        options.put("receipt", "booking_" + booking.getId());

        Order order = razorpayClient.orders.create(options);

        System.out.println(order.toString());

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.CREATED);
        payment.setRazorpayOrderId(order.get("id"));

        Payment saved = paymentRepository.save(payment);

        return PaymentMapper.toResponse(saved);
    }
}