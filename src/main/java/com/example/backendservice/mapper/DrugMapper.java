package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.request.DrugRequest;

public class DrugMapper {
    public static DrugDto entityToDto(DrugEntity drug) {
        return DrugDto.builder()
                .id(drug.getId())
                .drugName(drug.getDrugName())
                .imagePath(drug.getImagePath())
                .price(drug.getPrice())
                .registerNumber(drug.getRegisterNumber())
                .activeIngredient(drug.getInteractions())
                .usageDosage(drug.getUsageAndDosage())
                .dosageForms(drug.getDosageForm())
                .sideEffects(drug.getSideEffects())
                .drugStorage(drug.getDrugStorage())
                .remarks(drug.getRemarks())
                .label(drug.getLabel())
                .lastModify(drug.getLastModify().getTime())
                .build();
    }

    public static DrugEntity requestToEntity(DrugRequest drug) {
        return DrugEntity.builder()
                .drugName(drug.getDrugName())
                .price(drug.getPrice())
                .indications(drug.getActiveIngredient())
                .registerNumber(drug.getRegisterNumber())
                .dosageForm(drug.getDosageForms())
                .usageAndDosage(drug.getUsageDosage())
                .sideEffects(drug.getSideEffects())
                .drugStorage(drug.getDrugStorage())
                .remarks(drug.getRemarks())
                .label(drug.getLabel())
                .build();
    }
}
