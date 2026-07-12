package com.renthub.admin.service;

import com.renthub.admin.dto.AdminUserResponse;
import com.renthub.admin.dto.DashboardResponse;
import com.renthub.listing.dto.ListingResponse;
import com.renthub.booking.dto.BookingResponse;

import java.util.List;

public interface AdminService {

    DashboardResponse getDashboard();

    List<AdminUserResponse> getAllUsers();

    AdminUserResponse getUser(Long id);

    void blockUser(Long id);

    void unblockUser(Long id);

    void deleteUser(Long id);
    List<ListingResponse> getAllListings();

    void approveListing(Long listingId);

    void rejectListing(Long listingId);

    void deleteListing(Long listingId);

    List<BookingResponse> getAllBookings();
}