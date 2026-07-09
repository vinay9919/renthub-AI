package com.renthub.booking.repository;

import com.renthub.booking.entity.Booking;
import com.renthub.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {
                @Override
@EntityGraph(attributePaths = {
        "listing",
        "listing.owner"
})
Optional<Booking> findById(Long id);

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