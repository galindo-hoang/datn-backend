package com.example.backendservice.service;

import com.example.backendservice.model.dto.LastUpload;
import com.example.backendservice.model.dto.PrescriptionDto;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.model.request.PrescriptionRequest;

import java.util.List;

public interface PrescriptionService {
    List<PrescriptionDto> getListImage(FilterRequest filter);

    void deletePrescription(Long id);

    List<LastUpload> listLastUpload(Long startYear, Long startMonth, Long endYear, Long endMonth);

    PrescriptionDto addPrescription(PrescriptionRequest request);
}
