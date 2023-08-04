package com.example.backendservice.repository;

import com.example.backendservice.model.entity.prescription.PrescriptionEntity;
import com.querydsl.core.Tuple;

import java.util.List;

public interface PrescriptionRepositoryCustom {
    List<PrescriptionEntity> findTopUpload(Long offset, Long size, Boolean asc);

    List<Tuple> analyzePrescription(String startDate, String endDate);

    List<Tuple> analyzeRateByMonth(Integer month, Integer year);

}
