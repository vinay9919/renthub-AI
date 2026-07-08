package com.renthub.listing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

}