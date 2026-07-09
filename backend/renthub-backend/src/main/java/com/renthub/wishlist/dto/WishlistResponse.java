package com.renthub.wishlist.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WishlistResponse {

    private Long id;

    private Long listingId;

    private String listingTitle;

    private String city;

    private String area;

    private String imageUrl;

    private String price;
}