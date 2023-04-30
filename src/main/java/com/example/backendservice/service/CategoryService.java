package com.example.backendservice.service;

import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.request.CategoryRequest;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    CategoryDto addCategory(CategoryRequest categoryRequest);
    void removeCategory(CategoryRequest categoryRequest);
    CategoryDto findDetailsCategory(Map<String, Object> request);
    List<CategoryDto> findListCategories(Long offSet, Long limit);
}
