package com.example.backendservice.service;

import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryRequest categoryRequest);

    void removeCategory(CategoryRequest categoryRequest);

    CategoryDto findDetailsCategory(Long id);

    List<CategoryDto> findCategoriesByName(String name, Long offset, Long size);

    Long getSize(String name);

    CategoryDto updateCategory(CategoryRequest categoryRequest);
}
