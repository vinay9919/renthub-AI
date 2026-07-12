package com.renthub.listing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ListingImageResponse {

    private Long id;

    private String imageUrl;

    private Boolean coverImage;

}