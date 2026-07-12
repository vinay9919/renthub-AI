package com.renthub.listing.repository;

import com.renthub.listing.entity.ListingAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingAttributeRepository
        extends JpaRepository<ListingAttribute,Long> {
}