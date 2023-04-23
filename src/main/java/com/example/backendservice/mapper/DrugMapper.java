package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.request.DrugRequest;

public class DrugMapper {
    public static DrugDto entityToDto(DrugEntity drug) {
        return null;
    }

    public static DrugEntity requestToEntity(DrugRequest drug) {
        DrugEntity.builder()
                .drugName(drug.getDrugName())
                .registerNumber(drug.getRegisterNumber())
                .ac
    }
}
