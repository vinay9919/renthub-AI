package com.renthub.listing.repository;

import com.renthub.listing.entity.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingImageRepository
        extends JpaRepository<ListingImage, Long> {

    List<ListingImage> findByListingId(Long listingId);

}