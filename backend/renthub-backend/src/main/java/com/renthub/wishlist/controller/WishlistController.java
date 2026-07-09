package com.renthub.wishlist.controller;

import com.renthub.wishlist.dto.WishlistResponse;
import com.renthub.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{userId}/{listingId}")
    public WishlistResponse add(
            @PathVariable Long userId,
            @PathVariable Long listingId) {

        return wishlistService.add(userId, listingId);
    }

    @DeleteMapping("/{userId}/{listingId}")
    public void remove(
            @PathVariable Long userId,
            @PathVariable Long listingId) {

        wishlistService.remove(userId, listingId);
    }

    @GetMapping("/{userId}")
    public List<WishlistResponse> getUserWishlist(
            @PathVariable Long userId) {

        return wishlistService.getUserWishlist(userId);
    }

    @GetMapping("/{userId}/{listingId}/exists")
    public boolean exists(
            @PathVariable Long userId,
            @PathVariable Long listingId) {

        return wishlistService.exists(userId, listingId);
    }

    @GetMapping("/count/{listingId}")
    public long count(
            @PathVariable Long listingId) {

        return wishlistService.count(listingId);
    }
}