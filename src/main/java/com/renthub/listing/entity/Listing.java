package com.renthub.listing.entity;

import com.renthub.listing.model.ListingStatus;
import com.renthub.listing.model.ListingType;
import com.renthub.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "listings")
@Getter
@Setter
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owner of the listing
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Category (House, Car, Bike...)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal securityDeposit = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    @Enumerated(EnumType.STRING)
    private ListingStatus status = ListingStatus.DRAFT;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String area;

    private Double latitude;

    private Double longitude;

    private Integer views = 0;

    private Double rating = 0.0;

    @Column(nullable = false)
    private Boolean available = true;

    private LocalDateTime availableFrom;

    private LocalDateTime availableTo;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

}