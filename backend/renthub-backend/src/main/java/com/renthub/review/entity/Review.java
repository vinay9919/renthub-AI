package com.renthub.review.entity;

import com.renthub.booking.entity.Booking;
import com.renthub.listing.entity.Listing;
import com.renthub.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reviewer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    // Listing being reviewed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    // Booking through which review was given
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 3000)
    private String comment;

    @Column(length = 3000)
    private String ownerReply;

    private Boolean edited = false;

    private Integer helpfulCount = 0;

    private Boolean reported = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}