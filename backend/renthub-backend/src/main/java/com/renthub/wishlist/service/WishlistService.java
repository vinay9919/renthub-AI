package com.renthub.wishlist.service;

import com.renthub.wishlist.dto.WishlistResponse;

import java.util.List;

public interface WishlistService {

    WishlistResponse add(Long userId, Long listingId);

    void remove(Long userId, Long listingId);

    List<WishlistResponse> getUserWishlist(Long userId);

    boolean exists(Long userId, Long listingId);

    long count(Long listingId);

}