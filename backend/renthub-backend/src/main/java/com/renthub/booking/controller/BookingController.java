package com.renthub.booking.controller;

import com.renthub.booking.dto.BookingResponse;
import com.renthub.booking.dto.CreateBookingRequest;
import com.renthub.booking.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Bookings",
        description = "Booking Management APIs")
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    @Operation(

        summary = "Create Booking",

        description = "Customer creates a booking for a listing.")
@ApiResponses({

        @ApiResponse(

                responseCode = "200",

                description = "Booking created successfully"),

        @ApiResponse(

                responseCode = "404",

                description = "Listing not found"),

        @ApiResponse(

                responseCode = "400",

                description = "Invalid request")
})
@PreAuthorize("isAuthenticated()")
@PostMapping
public BookingResponse createBooking(
        @Valid
        @RequestBody CreateBookingRequest request) {

    return bookingService.createBooking(request);
}
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{bookingId}/complete")
    public BookingResponse completeBooking(
        @PathVariable Long bookingId) {

    return bookingService.completeBooking(bookingId);
}
   @Operation(
        summary = "Get My Bookings",
        description = """
                Returns all bookings created by
                the currently logged-in customer.
                JWT Authentication Required.
                """)
@ApiResponses({

        @ApiResponse(
                responseCode = "200",
                description = "Bookings fetched successfully"),

        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized"),

        @ApiResponse(
                responseCode = "403",
                description = "Access denied")
})
@PreAuthorize("isAuthenticated()")
@GetMapping("/my")
public List<BookingResponse> getMyBookings() {

    return bookingService.getMyBookings();

}
@Operation(
        summary = "Owner Bookings",
        description = """
                Returns all bookings received
                for listings owned by
                the currently logged-in owner.
                """)
@ApiResponses({

        @ApiResponse(
                responseCode = "200",
                description = "Bookings fetched"),

        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized")
})
@PreAuthorize("isAuthenticated()")
@GetMapping("/owner")
public List<BookingResponse> getOwnerBookings() {

    return bookingService.getOwnerBookings();

}
@Operation(
        summary = "Approve Booking",
        description = """
                Listing owner approves
                a pending booking.
                """)
@ApiResponses({

        @ApiResponse(
                responseCode = "200",
                description = "Booking approved"),

        @ApiResponse(
                responseCode = "404",
                description = "Booking not found"),

        @ApiResponse(
                responseCode = "400",
                description = "Booking already approved"),

        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized")
})
@PreAuthorize("isAuthenticated()")
@PutMapping("/{bookingId}/approve")
public BookingResponse approveBooking(
        @PathVariable Long bookingId) {

    return bookingService.approveBooking(bookingId);

}
@Operation(
        summary = "Reject Booking",
        description = "Owner rejects a booking request.")
@ApiResponses({

        @ApiResponse(
                responseCode = "200",
                description = "Booking rejected"),

        @ApiResponse(
                responseCode = "404",
                description = "Booking not found")
})
@PreAuthorize("isAuthenticated()")
@PutMapping("/{bookingId}/reject")
public BookingResponse rejectBooking(
        @PathVariable Long bookingId) {

    return bookingService.rejectBooking(bookingId);

}
@Operation(
        summary = "Cancel Booking",
        description = "Customer cancels a booking.")
@ApiResponses({

        @ApiResponse(
                responseCode = "200",
                description = "Booking cancelled"),

        @ApiResponse(
                responseCode = "404",
                description = "Booking not found")
})
@PreAuthorize("isAuthenticated()")
@PutMapping("/{bookingId}/cancel")
public BookingResponse cancelBooking(
        @PathVariable Long bookingId) {

    return bookingService.cancelBooking(bookingId);

}
}