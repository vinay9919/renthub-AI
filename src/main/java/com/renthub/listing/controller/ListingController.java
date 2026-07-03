package com.renthub.listing.controller;
import java.util.List;
import com.renthub.listing.dto.CreateListingRequest;
import com.renthub.listing.dto.ListingResponse;
import com.renthub.listing.dto.UpdateListingRequest;
import com.renthub.listing.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.renthub.listing.dto.ListingImageResponse;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @PostMapping
    public ListingResponse createListing(
            @Valid
            @RequestBody CreateListingRequest request) {

        return listingService.createListing(request);

    }
    @GetMapping
public Page<ListingResponse> getAllListings(

        @RequestParam(defaultValue = "0") int page,

        @RequestParam(defaultValue = "10") int size,

        @RequestParam(defaultValue = "createdAt") String sortBy) {

    return listingService.getAllListings(
            page,
            size,
            sortBy);

}

@GetMapping("/{id}")
public ListingResponse getListingById(
        @PathVariable Long id) {

    return listingService.getListingById(id);

}
@PutMapping("/{id}")
public ListingResponse updateListing(
        @PathVariable Long id,
        @Valid @RequestBody UpdateListingRequest request) {

    return listingService.updateListing(id, request);

}

@DeleteMapping("/{id}")
public void deleteListing(
        @PathVariable Long id) {

    listingService.deleteListing(id);

}
@GetMapping("/search")
public List<ListingResponse> searchListings(

        @RequestParam(required = false) String city,

        @RequestParam(required = false) String category) {

    return listingService.searchListings(city, category);

}
@PostMapping("/{listingId}/images")
public List<ListingImageResponse> uploadImages(

        @PathVariable Long listingId,

        @RequestParam("files") MultipartFile[] files) {

    return listingService.uploadImages(
            listingId,
            files);
}
}