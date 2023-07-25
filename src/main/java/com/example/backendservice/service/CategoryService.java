package com.example.backendservice.service;

import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.model.request.FilterRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryRequest categoryRequest);

    void removeCategory(CategoryRequest categoryRequest);

    CategoryDto findDetailsCategory(Long id);

    List<CategoryDto> findCategoriesByText(FilterRequest filter);

    Long getSize(String name);

    CategoryDto updateCategory(CategoryRequest categoryRequest);
}
