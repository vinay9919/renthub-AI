package com.renthub.listing.repository;

import com.renthub.listing.entity.Listing;
import com.renthub.listing.model.ListingStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByStatus(ListingStatus status);

    List<Listing> findByCityIgnoreCase(String city);
    List<Listing> findByCityIgnoreCaseAndStatus(
        String city,
        ListingStatus status);

List<Listing> findByCategory_NameIgnoreCaseAndStatus(
        String category,
        ListingStatus status);

List<Listing> findByCityIgnoreCaseAndCategory_NameIgnoreCaseAndStatus(
        String city,
        String category,
        ListingStatus status);

    @Override
    @EntityGraph(attributePaths = {"category", "owner"})
    List<Listing> findAll();

    @Override
    @EntityGraph(attributePaths = {"category", "owner"})
    Optional<Listing> findById(Long id);

}