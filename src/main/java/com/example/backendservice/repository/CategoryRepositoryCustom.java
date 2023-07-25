package com.example.backendservice.repository;

import com.example.backendservice.common.model.SortType;
import com.example.backendservice.model.entity.product.CategoryEntity;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<CategoryEntity> findCategoriesByText(String name, Long offset, Long size, SortType sortType, Boolean asc);

    List<CategoryEntity> findSizeByText(String name);
}
