package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.model.request.CategoryRequest;

public class CategoryMapper {
    public static CategoryEntity requestToEntity(CategoryRequest categoryRequest) {
        return CategoryEntity.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public static CategoryDto entityToDto(CategoryEntity category) {
        return CategoryDto.builder()
                .name(category.getName())
                .build();
    }
}
