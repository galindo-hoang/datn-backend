package com.example.backendservice.service;

import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.request.CategoryRequest;

public interface CategoryService {
    CategoryDto addCategory(CategoryRequest categoryRequest);
    void removeCategory(CategoryRequest categoryRequest);

}
