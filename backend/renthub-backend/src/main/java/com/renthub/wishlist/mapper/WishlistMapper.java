package com.renthub.wishlist.mapper;

import com.renthub.wishlist.dto.WishlistResponse;
import com.renthub.wishlist.entity.Wishlist;

public class WishlistMapper {

    private WishlistMapper() {
    }

    public static WishlistResponse toResponse(Wishlist wishlist) {

        return WishlistResponse.builder()
                .id(wishlist.getId())
                .listingId(wishlist.getListing().getId())
                .listingTitle(wishlist.getListing().getTitle())
                .city(wishlist.getListing().getCity())
                .area(wishlist.getListing().getArea())
                .imageUrl(
                        wishlist.getListing().getImages().isEmpty()
                                ? null
                                : wishlist.getListing()
                                        .getImages()
                                        .get(0)
                                        .getImageUrl())
                .price(wishlist.getListing().getPrice().toString())
                .build();
    }
}