package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.DrugEntity;

import java.util.List;

public interface DrugRepositoryCustom {
    List<DrugEntity> findDrugsByText(String text, Integer offset, Integer size);
}
