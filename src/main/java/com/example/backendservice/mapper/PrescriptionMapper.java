package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.PrescriptionDto;
import com.example.backendservice.model.entity.prescription.PrescriptionEntity;

public class PrescriptionMapper {
    public static PrescriptionDto entityToDto (PrescriptionEntity prescription) {
        return PrescriptionDto.builder()
                .id(prescription.getId())
                .imagePath(prescription.getImagePath())
                .createdOn(prescription.getCreatedOn().getTime())
                .bytes(prescription.getBytes())
                .width(prescription.getWidth())
                .height(prescription.getHeight())
                .build();

    }
}
