package com.example.backendservice.service;

import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.request.DrugRequest;
import com.example.backendservice.model.request.FilterRequest;

import java.util.List;
import java.util.Map;

public interface DrugService {
    List<DrugDto> findDrugsByText(FilterRequest filter);

    List<DrugDto> findDrugsByCategory(FilterRequest filter);

    List<DrugDto> findTopSearchDrugs(FilterRequest build);

    DrugDto findDrugById(Long Id);

    DrugDto addDrug(DrugRequest drug);

    DrugDto updateDrug(DrugRequest drug);

    void removeDrug(Long drugId);

    Long getSize(String name);

    Map<String, Long> ListLastUpdate(Long startYear, Long startMonth, Long endYear, Long endMonth);
}
