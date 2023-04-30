package com.example.backendservice.service;

import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.model.request.DrugRequest;

import java.util.List;
import java.util.Map;

public interface DrugService {
    // drug name
//    List<DrugEntity> findDrugsByText(FilterRequest filter);
//    List<DrugEntity> findsDrugByCategory(FilterRequest filter);

//    List<DrugDto> findDrugsByFilter(FilterRequest filterRequest);
    List<DrugDto> findDrugsByFilter(Map<String, Object> filter);

    DrugDto findDrugById(Long Id);

    DrugDto addDrug(DrugRequest drug);
    DrugDto updateDrug(DrugRequest drug);
    void removeDrug(Long drugId);
}
