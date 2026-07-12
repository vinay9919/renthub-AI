package com.renthub.booking.service;

import com.renthub.booking.dto.BookingResponse;
import com.renthub.booking.dto.CreateBookingRequest;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);

    List<BookingResponse> getMyBookings();

    List<BookingResponse> getOwnerBookings();
    
    BookingResponse approveBooking(Long bookingId);

    BookingResponse rejectBooking(Long bookingId);

    BookingResponse cancelBooking(Long bookingId);
    
    BookingResponse completeBooking(Long bookingId);
}