package com.renthub.review.mapper;

import com.renthub.review.dto.ReviewResponse;
import com.renthub.review.entity.Review;

public class ReviewMapper {

    private ReviewMapper() {
    }

    public static ReviewResponse toResponse(Review review) {

        return ReviewResponse.builder()
                .id(review.getId())
                .reviewerId(review.getReviewer().getId())
                .reviewerName(review.getReviewer().getFullName())
                .rating(review.getRating())
                .comment(review.getComment())
                .ownerReply(review.getOwnerReply())
                .helpfulCount(review.getHelpfulCount())
                .createdAt(review.getCreatedAt())
                .build();
    }
}