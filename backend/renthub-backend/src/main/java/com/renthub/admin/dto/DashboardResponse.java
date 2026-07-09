package com.renthub.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResponse {

    private long totalUsers;

    private long totalListings;

    private long totalBookings;

    private long totalPayments;

    private long totalReviews;

    private long pendingKyc;

}