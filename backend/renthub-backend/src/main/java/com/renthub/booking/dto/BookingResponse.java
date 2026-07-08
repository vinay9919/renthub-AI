package com.renthub.booking.dto;

import com.renthub.booking.model.BookingStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BookingResponse {

    private Long id;

    private Long listingId;

    private String listingTitle;

    private String customerName;

    private LocalDate startDate;

    private LocalDate endDate;

    private BookingStatus status;
}