package com.renthub.admin.controller;

import com.renthub.admin.dto.DashboardResponse;
import com.renthub.admin.service.AdminService;
import com.renthub.listing.dto.ListingResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.renthub.admin.dto.AdminUserResponse;
import com.renthub.booking.dto.BookingResponse;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {

        return adminService.getDashboard();

    }
    @PreAuthorize("hasRole('ADMIN')")
@GetMapping("/users")
public List<AdminUserResponse> getAllUsers() {

    return adminService.getAllUsers();
}
@GetMapping("/users/{id}")
@PreAuthorize("hasRole('ADMIN')")
public AdminUserResponse getUser(
        @PathVariable Long id) {

    return adminService.getUser(id);
}
@PutMapping("/users/{id}/block")
@PreAuthorize("hasRole('ADMIN')")
public void blockUser(
        @PathVariable Long id) {

    adminService.blockUser(id);
}
@PutMapping("/users/{id}/unblock")
@PreAuthorize("hasRole('ADMIN')")
public void unblockUser(
        @PathVariable Long id) {

    adminService.unblockUser(id);
}
@DeleteMapping("/users/{id}")
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(
        @PathVariable Long id) {

    adminService.deleteUser(id);
}
@GetMapping("/listings")
@PreAuthorize("hasRole('ADMIN')")
public List<ListingResponse> getAllListings() {

    return adminService.getAllListings();
}

@PutMapping("/listings/{id}/approve")
@PreAuthorize("hasRole('ADMIN')")
public void approveListing(@PathVariable Long id) {

    adminService.approveListing(id);
}

@PutMapping("/listings/{id}/reject")
@PreAuthorize("hasRole('ADMIN')")
public void rejectListing(@PathVariable Long id) {

    adminService.rejectListing(id);
}

@DeleteMapping("/listings/{id}")
@PreAuthorize("hasRole('ADMIN')")
public void deleteListing(@PathVariable Long id) {

    adminService.deleteListing(id);
}
// ===========================
// Booking Management
// ===========================

@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/bookings")
public String getAllBookings() {
    return "Booking API Working";
}
}