package com.renthub.listing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "listing_images")
@Getter
@Setter
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="listing_id")
    private Listing listing;

    @Column(nullable = false)
    private String imageUrl;

    private boolean thumbnail;

    private Integer displayOrder;

}