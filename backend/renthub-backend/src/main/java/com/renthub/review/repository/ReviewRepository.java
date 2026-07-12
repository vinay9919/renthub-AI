package com.renthub.review.repository;

import com.renthub.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByListingIdOrderByCreatedAtDesc(Long listingId);

    Optional<Review> findByBookingId(Long bookingId);

    List<Review> findByReviewerId(Long reviewerId);

    long countByListingId(Long listingId);

}