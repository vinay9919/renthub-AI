package com.renthub.listing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateListingRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String city;

    @NotBlank
    private String area;

}