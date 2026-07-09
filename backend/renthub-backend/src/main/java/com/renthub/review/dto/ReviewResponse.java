package com.renthub.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {

    private Long id;

    private Long reviewerId;

    private String reviewerName;

    private Integer rating;

    private String comment;

    private String ownerReply;

    private Integer helpfulCount;

    private LocalDateTime createdAt;

}