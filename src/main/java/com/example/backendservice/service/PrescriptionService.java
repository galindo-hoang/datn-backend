package com.example.backendservice.service;

import com.example.backendservice.model.dto.ImageDto;

import java.util.List;

public interface PrescriptionService {
    List<ImageDto> getListImage(Long size, Long offset);
}
