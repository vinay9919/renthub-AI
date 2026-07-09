package com.renthub.booking.service.impl;

import com.renthub.booking.dto.BookingResponse;
import com.renthub.booking.dto.CreateBookingRequest;
import com.renthub.booking.entity.Booking;
import com.renthub.booking.exception.BookingNotFoundException;
import com.renthub.booking.mapper.BookingMapper;
import com.renthub.booking.model.BookingStatus;
import com.renthub.booking.repository.BookingRepository;
import com.renthub.booking.service.BookingService;
import com.renthub.exception.AccessDeniedException;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.listing.entity.Listing;
import com.renthub.listing.model.ListingStatus;
import com.renthub.listing.repository.ListingRepository;
import com.renthub.security.service.AuthenticatedUserService;
import com.renthub.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {

        Listing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Listing not found"));

        User customer = authenticatedUserService.getCurrentUser();

        if (listing.getOwner().getId().equals(customer.getId())) {
            throw new IllegalArgumentException("You cannot book your own listing");
        }

        if (listing.getStatus() != ListingStatus.ACTIVE) {
            throw new IllegalArgumentException("Listing is not available for booking");
        }

        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        boolean overlap = bookingRepository
                .existsByListingIdAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        listing.getId(),
                        List.of(
                                BookingStatus.PENDING,
                                BookingStatus.APPROVED
                        ),
                        request.getEndDate(),
                        request.getStartDate()
                );

        if (overlap) {
            throw new IllegalArgumentException("Selected dates are not available");
        }

        Booking booking = new Booking();

        booking.setListing(listing);
        booking.setCustomer(customer);
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());

        Booking saved = bookingRepository.save(booking);

return BookingMapper.toResponse(saved);
    }


    @Override
@Transactional(readOnly = true)
public List<BookingResponse> getMyBookings() {

    Long customerId = authenticatedUserService.getCurrentUserId();

        return bookingRepository.findByCustomerId(customerId)
                .stream()
                .map(BookingMapper::toResponse)
                .toList();
}

@Override
@Transactional(readOnly = true)
public List<BookingResponse> getOwnerBookings() {

    Long ownerId = authenticatedUserService.getCurrentUserId();

    return bookingRepository.findByListingOwnerId(ownerId)
        .stream()
        .map(BookingMapper::toResponse)
        .toList();
}

@Override
@Transactional
public BookingResponse approveBooking(Long bookingId) {

    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() ->
                    new BookingNotFoundException("Booking not found"));

    Long ownerId = authenticatedUserService.getCurrentUserId();

    if (!booking.getListing().getOwner().getId().equals(ownerId)) {
        throw new AccessDeniedException(
                "You are not allowed to approve this booking");
    }

    booking.setStatus(BookingStatus.APPROVED);

bookingRepository.save(booking);

return BookingMapper.toResponse(booking);
}

@Override
@Transactional
public BookingResponse rejectBooking(Long bookingId) {

    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() ->
                    new BookingNotFoundException("Booking not found"));

    Long ownerId = authenticatedUserService.getCurrentUserId();

    if (!booking.getListing().getOwner().getId().equals(ownerId)) {
        throw new AccessDeniedException(
                "You are not allowed to reject this booking");
    }

         booking.setStatus(BookingStatus.REJECTED);

        bookingRepository.save(booking);

        return BookingMapper.toResponse(booking);
}

@Override
@Transactional
public BookingResponse cancelBooking(Long bookingId) {

    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() ->
                    new BookingNotFoundException("Booking not found"));

    Long customerId = authenticatedUserService.getCurrentUserId();

    if (!booking.getCustomer().getId().equals(customerId)) {
        throw new AccessDeniedException(
                "You are not allowed to cancel this booking");
    }

    if (booking.getStatus() != BookingStatus.PENDING) {
        throw new IllegalArgumentException(
                "Only pending bookings can be cancelled");
    }

   booking.setStatus(BookingStatus.CANCELLED);

bookingRepository.save(booking);

return BookingMapper.toResponse(booking);
}
@Override
@Transactional
public BookingResponse completeBooking(Long bookingId) {

    System.out.println(">>> completeBooking called");

    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() ->
                    new BookingNotFoundException("Booking not found"));

    Long ownerId = authenticatedUserService.getCurrentUserId();

    System.out.println("Logged-in user: " + ownerId);
    System.out.println("Listing owner: " + booking.getListing().getOwner().getId());
    System.out.println("Booking status: " + booking.getStatus());

    if (!booking.getListing().getOwner().getId().equals(ownerId)) {
        throw new AccessDeniedException(
                "You are not allowed to complete this booking");
    }

    booking.setStatus(BookingStatus.COMPLETED);

    bookingRepository.save(booking);

    return BookingMapper.toResponse(booking);
}
}