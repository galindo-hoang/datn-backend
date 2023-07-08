package com.example.backendservice.repository;

import com.example.backendservice.common.model.SortType;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Map;

public interface DrugRepositoryCustom {
    List<DrugEntity> findDrugsByText(String text, Long offset, Long size, SortType typeSort, Boolean asc);

    List<DrugEntity> findAllDrugsByText(String text);

    List<DrugEntity> findDrugsByCategory(String categoryName, Long offset, Long size, SortType typeSort, Boolean asc);

    List<Tuple> findTopSearch(Long offset, Long size, Boolean asc);

    List<Tuple> findLastUpdate(String startDate, String endDate);
}
