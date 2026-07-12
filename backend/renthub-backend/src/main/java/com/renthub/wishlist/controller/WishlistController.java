package com.renthub.wishlist.controller;

import com.renthub.security.service.AuthenticatedUserService;
import com.renthub.wishlist.dto.WishlistResponse;
import com.renthub.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final AuthenticatedUserService authenticatedUserService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{listingId}")
    public WishlistResponse add(
        @PathVariable Long listingId) {

    Long userId = authenticatedUserService
            .getCurrentUser()
            .getId();

    return wishlistService.add(userId, listingId);
}
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{listingId}")
public void remove(
        @PathVariable Long listingId) {

    Long userId = authenticatedUserService.getCurrentUserId();

    wishlistService.remove(userId, listingId);
}
    @PreAuthorize("isAuthenticated()")
    @GetMapping
public List<WishlistResponse> getUserWishlist() {

    Long userId = authenticatedUserService.getCurrentUserId();

    return wishlistService.getUserWishlist(userId);
}
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{listingId}/exists")
public boolean exists(
        @PathVariable Long listingId) {

    Long userId = authenticatedUserService.getCurrentUserId();

    return wishlistService.exists(userId, listingId);
}
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/count/{listingId}")
    public long count(
            @PathVariable Long listingId) {

        return wishlistService.count(listingId);
    }
}