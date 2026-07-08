package com.renthub.listing.service.impl;

import com.renthub.listing.dto.CategoryRequest;
import com.renthub.listing.dto.CategoryResponse;
import com.renthub.listing.entity.Category;
import com.renthub.listing.repository.CategoryRepository;
import com.renthub.listing.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.getName());

        Category savedCategory = categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList();
    }
}