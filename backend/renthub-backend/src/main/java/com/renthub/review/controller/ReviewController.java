package com.renthub.review.controller;

import com.renthub.review.dto.CreateReviewRequest;
import com.renthub.review.dto.ReplyReviewRequest;
import com.renthub.review.dto.ReviewResponse;
import com.renthub.review.service.ReviewService;
import com.renthub.security.service.AuthenticatedUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthenticatedUserService authenticatedUserService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ReviewResponse create(
            @RequestBody CreateReviewRequest request) {

        Long reviewerId = authenticatedUserService.getCurrentUserId();

        return reviewService.create(reviewerId, request);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/listing/{listingId}")
    public List<ReviewResponse> getListingReviews(
            @PathVariable Long listingId) {

        return reviewService.getListingReviews(listingId);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{reviewId}/reply")
    public ReviewResponse reply(
            @PathVariable Long reviewId,
            @RequestBody ReplyReviewRequest request) {

        return reviewService.reply(reviewId, request);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{reviewId}")
    public void delete(
            @PathVariable Long reviewId) {

        reviewService.delete(reviewId);
    }
}