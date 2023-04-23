package com.example.backendservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugDto {
    private Long id;
    private String drugName;
    private String registerNumber;
    private String activeIngredient;
    private String DosageForms;
    private String UsageDosage;
    private String sideEffects;
    private String drugStorage;
    private String remarks;
}
