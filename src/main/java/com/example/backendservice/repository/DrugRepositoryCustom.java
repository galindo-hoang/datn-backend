package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.DrugEntity;

import java.util.List;

public interface DrugRepositoryCustom {
    List<DrugEntity> findDrugsByText(String text, Long offset, Long size);
    List<DrugEntity> findDrugsByCategory(Long categoryId, Long offset, Long size);

}
