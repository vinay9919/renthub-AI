package com.renthub.review.service;

import com.renthub.review.dto.CreateReviewRequest;
import com.renthub.review.dto.ReplyReviewRequest;
import com.renthub.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse create(Long reviewerId, CreateReviewRequest request);

    List<ReviewResponse> getListingReviews(Long listingId);

    ReviewResponse reply(Long reviewId, ReplyReviewRequest request);

    void delete(Long reviewId);

}