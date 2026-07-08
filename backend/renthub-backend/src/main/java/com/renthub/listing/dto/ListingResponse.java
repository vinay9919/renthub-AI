package com.renthub.listing.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ListingResponse {

    private Long id;

    private String title;

    private String description;

    private String category;

    private BigDecimal price;

    private String city;

    private String area;

    private String status;

}