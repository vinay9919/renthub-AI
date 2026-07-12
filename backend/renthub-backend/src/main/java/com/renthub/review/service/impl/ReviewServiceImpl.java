package com.renthub.review.service.impl;

import com.renthub.booking.entity.Booking;
import com.renthub.booking.model.BookingStatus;
import com.renthub.booking.repository.BookingRepository;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.listing.entity.Listing;
import com.renthub.listing.repository.ListingRepository;
import com.renthub.notification.dto.NotificationRequest;
import com.renthub.notification.model.NotificationType;
import com.renthub.notification.service.NotificationService;
import com.renthub.review.dto.CreateReviewRequest;
import com.renthub.review.dto.ReplyReviewRequest;
import com.renthub.review.dto.ReviewResponse;
import com.renthub.review.entity.Review;
import com.renthub.review.mapper.ReviewMapper;
import com.renthub.review.repository.ReviewRepository;
import com.renthub.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final BookingRepository bookingRepository;

    private final ListingRepository listingRepository;

    private final NotificationService notificationService;

@Override
@Transactional
public ReviewResponse create(
        Long reviewerId,
        CreateReviewRequest request) {

    Booking booking = bookingRepository.findById(
            request.getBookingId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Booking not found"));

    // Booking must belong to reviewer
    if (!booking.getCustomer().getId().equals(reviewerId)) {
        throw new IllegalArgumentException(
                "You can review only your own booking");
    }

    // Booking must be completed
    if (booking.getStatus() != BookingStatus.COMPLETED) {
        throw new IllegalArgumentException(
                "Only completed bookings can be reviewed");
    }

    // One review per booking
    if (reviewRepository.findByBookingId(booking.getId()).isPresent()) {
        throw new IllegalArgumentException(
                "Review already submitted");
    }

    // Rating validation
    if (request.getRating() < 1 || request.getRating() > 5) {
        throw new IllegalArgumentException(
                "Rating must be between 1 and 5");
    }

    Review review = new Review();

    review.setBooking(booking);
    review.setListing(booking.getListing());
    review.setReviewer(booking.getCustomer());
    review.setRating(request.getRating());
    review.setComment(request.getComment());

    Review saved = reviewRepository.save(review);

    return ReviewMapper.toResponse(saved);
}
@Override
@Transactional(readOnly = true)
public List<ReviewResponse> getListingReviews(Long listingId) {

    return reviewRepository
            .findByListingIdOrderByCreatedAtDesc(listingId)
            .stream()
            .map(ReviewMapper::toResponse)
            .toList();
}

@Override
@Transactional
public ReviewResponse reply(
        Long reviewId,
        ReplyReviewRequest request) {

    Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Review not found"));

    review.setOwnerReply(request.getOwnerReply());

    reviewRepository.save(review);

    return ReviewMapper.toResponse(review);
}

@Override
@Transactional
public void delete(Long reviewId) {

    if (!reviewRepository.existsById(reviewId)) {
        throw new ResourceNotFoundException("Review not found");
    }

    reviewRepository.deleteById(reviewId);
}

}