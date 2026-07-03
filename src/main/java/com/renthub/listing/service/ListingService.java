package com.renthub.listing.service;

import com.renthub.listing.dto.CreateListingRequest;
import com.renthub.listing.dto.ListingImageResponse;
import com.renthub.listing.dto.ListingResponse;
import com.renthub.listing.dto.UpdateListingRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListingService {

    ListingResponse createListing(CreateListingRequest request);

    List<ListingResponse> getAllListings();

    Page<ListingResponse> getAllListings(
            int page,
            int size,
            String sortBy);

    ListingResponse getListingById(Long id);

    List<ListingImageResponse> uploadImages(
        Long listingId,
        MultipartFile[] files);

    ListingResponse updateListing(
            Long id,
            UpdateListingRequest request);

    void deleteListing(Long id);

    List<ListingResponse> searchListings(
            String city,
            String category);
}