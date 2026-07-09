package com.renthub.booking.controller;

import com.renthub.booking.dto.BookingResponse;
import com.renthub.booking.dto.CreateBookingRequest;
import com.renthub.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public BookingResponse createBooking(
            @Valid
            @RequestBody CreateBookingRequest request) {

        return bookingService.createBooking(request);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my")
public List<BookingResponse> getMyBookings() {

    return bookingService.getMyBookings();

}
@PreAuthorize("isAuthenticated()")
@GetMapping("/owner")
public List<BookingResponse> getOwnerBookings() {

    return bookingService.getOwnerBookings();

}
@PreAuthorize("isAuthenticated()")
@PutMapping("/{bookingId}/approve")
public BookingResponse approveBooking(
        @PathVariable Long bookingId) {
            System.out.println("approveBooking reached: " + bookingId);

    return bookingService.approveBooking(bookingId);

}
@PreAuthorize("isAuthenticated()")
@PutMapping("/{bookingId}/reject")
public BookingResponse rejectBooking(
        @PathVariable Long bookingId) {

    return bookingService.rejectBooking(bookingId);
}
@PreAuthorize("isAuthenticated()")
@PutMapping("/{bookingId}/cancel")
public BookingResponse cancelBooking(
        @PathVariable Long bookingId) {

    return bookingService.cancelBooking(bookingId);
}
}