package com.example.backendservice.service;

import com.example.backendservice.model.dto.LastUpload;
import com.example.backendservice.model.dto.PrescriptionDto;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.model.request.PrescriptionRequest;

import java.util.List;
import java.util.Map;

public interface PrescriptionService {
    List<PrescriptionDto> getListImage(FilterRequest filter);

    void deletePrescription(PrescriptionRequest request);

    List<LastUpload> listLastUpload(Long startYear, Long startMonth, Long endYear, Long endMonth);

    PrescriptionDto addPrescription(PrescriptionRequest request);

    Long getSize();

    PrescriptionDto getById(Long id);

    Map<String, Long> analyzeRate(Integer month, Integer year);
}
