package com.renthub.booking.repository;

import com.renthub.booking.entity.Booking;
import com.renthub.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByListingOwnerId(Long ownerId);

    List<Booking> findByListingId(Long listingId);

    boolean existsByListingIdAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long listingId,
            List<BookingStatus> statuses,
            LocalDate endDate,
            LocalDate startDate
    );
}