package com.renthub.booking.mapper;

import com.renthub.booking.dto.BookingResponse;
import com.renthub.booking.entity.Booking;


public class BookingMapper {

    private BookingMapper() {
    }

    public static BookingResponse toResponse(Booking booking) {

        return BookingResponse.builder()
                .id(booking.getId())
                .listingId(booking.getListing().getId())
                .listingTitle(booking.getListing().getTitle())
                .customerName(booking.getCustomer().getFullName())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .status(booking.getStatus())
                .build();
    }
}