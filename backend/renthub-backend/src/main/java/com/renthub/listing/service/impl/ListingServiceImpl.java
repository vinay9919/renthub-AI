package com.renthub.listing.service.impl;

import com.renthub.exception.ResourceNotFoundException;
import com.renthub.listing.dto.CreateListingRequest;
import com.renthub.listing.dto.ListingResponse;
import com.renthub.listing.dto.UpdateListingRequest;
import com.renthub.listing.entity.Category;
import com.renthub.listing.entity.Listing;
import com.renthub.listing.exception.CategoryNotFoundException;
import com.renthub.listing.exception.ListingNotFoundException;
import com.renthub.listing.mapper.ListingMapper;
import com.renthub.listing.repository.CategoryRepository;
import com.renthub.listing.repository.ListingRepository;
import com.renthub.listing.service.ListingService;
import com.renthub.user.entity.User;
import com.renthub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.renthub.cloudinary.service.CloudinaryService;
import com.renthub.listing.dto.ListingImageResponse;
import com.renthub.listing.entity.ListingImage;
import com.renthub.listing.repository.ListingImageRepository;
import org.springframework.web.multipart.MultipartFile;
import com.renthub.security.service.AuthenticatedUserService;
import com.renthub.exception.AccessDeniedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ListingImageRepository listingImageRepository;
    private final CloudinaryService cloudinaryService;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    @Transactional
    public ListingResponse createListing(CreateListingRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found"));

        // Temporary: replace with logged-in user after JWT integration
       User owner = authenticatedUserService.getCurrentUser();

        Listing listing = new Listing();

        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setCategory(category);
        listing.setOwner(owner);
        listing.setPrice(request.getPrice());
        listing.setCity(request.getCity());
        listing.setArea(request.getArea());

        listing.setStatus(
        com.renthub.listing.model.ListingStatus.ACTIVE);
        listing.setArea(request.getArea());

        Listing savedListing = listingRepository.save(listing);

        return ListingMapper.toResponse(savedListing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListingResponse> getAllListings() {

        return listingRepository.findAll()
                .stream()
                .map(ListingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ListingResponse getListingById(Long id) {

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() ->
                        new ListingNotFoundException("Listing not found"));

        return ListingMapper.toResponse(listing);
    }
    @Override
@Transactional(readOnly = true)
public Page<ListingResponse> getAllListings(
        int page,
        int size,
        String sortBy) {

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(sortBy));

    return listingRepository.findAll(pageable)
            .map(ListingMapper::toResponse);

}

   @Override
@Transactional
public ListingResponse updateListing(Long id, UpdateListingRequest request) {
    Listing listing = listingRepository.findById(id)
            .orElseThrow(() ->
                    new ListingNotFoundException("Listing not found"));

    validateListingOwner(listing);
    Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() ->
                    new CategoryNotFoundException("Category not found"));

    listing.setTitle(request.getTitle());
    listing.setDescription(request.getDescription());
    listing.setCategory(category);
    listing.setPrice(request.getPrice());
    listing.setCity(request.getCity());
    listing.setArea(request.getArea());
    listing.setUpdatedAt(java.time.LocalDateTime.now());

    Listing updatedListing = listingRepository.save(listing);

    return ListingMapper.toResponse(updatedListing);
}


    @Override
@Transactional
public void deleteListing(Long id) {

    Listing listing = listingRepository.findById(id)
            .orElseThrow(() ->
                    new ListingNotFoundException("Listing not found"));

        validateListingOwner(listing);
    listing.setStatus(com.renthub.listing.model.ListingStatus.INACTIVE);
    listing.setUpdatedAt(java.time.LocalDateTime.now());

    listingRepository.save(listing);
}
@Override
@Transactional
public List<ListingImageResponse> uploadImages(
        Long listingId,
        MultipartFile[] files) {
    Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() ->
                    new ListingNotFoundException("Listing not found"));

    validateListingOwner(listing);
    List<ListingImageResponse> responses = new java.util.ArrayList<>();

    int order = listingImageRepository.findByListingId(listingId).size();

    boolean thumbnail = (order == 0);

    for (MultipartFile file : files) {

        String imageUrl = cloudinaryService.uploadImage(file);

        ListingImage image = new ListingImage();

        image.setListing(listing);
        image.setImageUrl(imageUrl);
        image.setThumbnail(thumbnail);
        image.setDisplayOrder(order);

        ListingImage savedImage = listingImageRepository.save(image);

        responses.add(
                ListingImageResponse.builder()
                        .id(savedImage.getId())
                        .imageUrl(savedImage.getImageUrl())
                        .coverImage(savedImage.isThumbnail())
                        .build()
        );

        thumbnail = false;
        order++;
    }

    return responses;
}
@Override
@Transactional(readOnly = true)
public List<ListingResponse> searchListings(
        String city,
        String category) {

    List<Listing> listings;

    if (city != null && category != null) {

        listings = listingRepository
                .findByCityIgnoreCaseAndCategory_NameIgnoreCaseAndStatus(
                        city,
                        category,
                        com.renthub.listing.model.ListingStatus.ACTIVE);

    } else if (city != null) {

        listings = listingRepository
                .findByCityIgnoreCaseAndStatus(
                        city,
                        com.renthub.listing.model.ListingStatus.ACTIVE);

    } else if (category != null) {

        listings = listingRepository
                .findByCategory_NameIgnoreCaseAndStatus(
                        category,
                        com.renthub.listing.model.ListingStatus.ACTIVE);

    } else {

        listings = listingRepository.findByStatus(
                com.renthub.listing.model.ListingStatus.ACTIVE);

    }

    return listings.stream()
            .map(ListingMapper::toResponse)
            .toList();
}

private void validateListingOwner(Listing listing) {

    Long currentUserId = authenticatedUserService.getCurrentUserId();

    if (!listing.getOwner().getId().equals(currentUserId)) {
        throw new AccessDeniedException(
                "You are not allowed to modify this listing");
    }
}
}