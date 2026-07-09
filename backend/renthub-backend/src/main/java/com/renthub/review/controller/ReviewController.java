package com.renthub.review.controller;

import com.renthub.review.dto.CreateReviewRequest;
import com.renthub.review.dto.ReplyReviewRequest;
import com.renthub.review.dto.ReviewResponse;
import com.renthub.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{reviewerId}")
    public ReviewResponse create(
            @PathVariable Long reviewerId,
            @RequestBody CreateReviewRequest request) {

        return reviewService.create(reviewerId, request);
    }

    @GetMapping("/listing/{listingId}")
    public List<ReviewResponse> getListingReviews(
            @PathVariable Long listingId) {

        return reviewService.getListingReviews(listingId);
    }

    @PutMapping("/{reviewId}/reply")
    public ReviewResponse reply(
            @PathVariable Long reviewId,
            @RequestBody ReplyReviewRequest request) {

        return reviewService.reply(reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    public void delete(
            @PathVariable Long reviewId) {

        reviewService.delete(reviewId);
    }
}