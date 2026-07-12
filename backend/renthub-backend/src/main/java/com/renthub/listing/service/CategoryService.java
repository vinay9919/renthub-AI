package com.renthub.listing.service;

import com.renthub.listing.dto.CategoryRequest;
import com.renthub.listing.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

}