package com.example.backendservice.repository.impl;

import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.repository.DrugRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DrugRepositoryCustomImpl implements DrugRepositoryCustom {
    @Override
    public List<DrugEntity> findDrugsByText(String text, Integer offset, Integer size) {
        return null;
    }
}
