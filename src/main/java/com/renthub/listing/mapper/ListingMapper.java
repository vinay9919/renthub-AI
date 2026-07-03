package com.renthub.listing.mapper;

import com.renthub.listing.dto.ListingResponse;
import com.renthub.listing.entity.Listing;

public class ListingMapper {

    private ListingMapper() {
    }

    public static ListingResponse toResponse(Listing listing) {

        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .category(listing.getCategory().getName())
                .price(listing.getPrice())
                .city(listing.getCity())
                .area(listing.getArea())
                .status(listing.getStatus().name())
                .build();

    }

}