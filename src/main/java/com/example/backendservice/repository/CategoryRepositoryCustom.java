package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.CategoryEntity;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<CategoryEntity> findCategoriesByText(String name, Long offset, Long size);
    List<CategoryEntity> findSizeByText(String name);
}
