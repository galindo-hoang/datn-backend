package com.example.backendservice.service;

import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.model.request.DrugRequest;

import java.util.List;

public interface DrugService {
    // drug name
    List<DrugEntity> findDrugsByText(FilterRequest filter);
    DrugDto findDrugById(Long Id);

    DrugDto addDrug(DrugRequest drug);
    DrugDto updateDrug(DrugRequest drug);
    void removeDrug(Long drugId);
}
