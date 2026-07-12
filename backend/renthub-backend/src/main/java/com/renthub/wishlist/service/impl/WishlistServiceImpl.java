package com.renthub.wishlist.service.impl;

import com.renthub.exception.ResourceNotFoundException;
import com.renthub.listing.entity.Listing;
import com.renthub.listing.repository.ListingRepository;
import com.renthub.user.entity.User;
import com.renthub.user.repository.UserRepository;
import com.renthub.wishlist.dto.WishlistResponse;
import com.renthub.wishlist.entity.Wishlist;
import com.renthub.wishlist.mapper.WishlistMapper;
import com.renthub.wishlist.repository.WishlistRepository;
import com.renthub.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    @Override
    @Transactional
    public WishlistResponse add(Long userId, Long listingId) {

        if (wishlistRepository.findByUserIdAndListingId(userId, listingId).isPresent()) {
            throw new IllegalArgumentException("Listing already in wishlist");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Listing not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setListing(listing);

        return WishlistMapper.toResponse(
                wishlistRepository.save(wishlist));
    }

    @Override
    @Transactional
    public void remove(Long userId, Long listingId) {

        Wishlist wishlist = wishlistRepository
                .findByUserIdAndListingId(userId, listingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wishlist item not found"));

        wishlistRepository.delete(wishlist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishlistResponse> getUserWishlist(Long userId) {

        return wishlistRepository.findByUserId(userId)
                .stream()
                .map(WishlistMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long userId, Long listingId) {

        return wishlistRepository
                .findByUserIdAndListingId(userId, listingId)
                .isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public long count(Long listingId) {

        return wishlistRepository.countByListingId(listingId);
    }
}