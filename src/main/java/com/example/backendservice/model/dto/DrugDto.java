package com.example.backendservice.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrugDto {
    private Long id;
    private String drugName;
    private Long price;
    private String registerNumber;
    private String activeIngredient;
    private String dosageForms;
    private String usageDosage;
    private String sideEffects;
    private String drugStorage;
    private String label;
    private String remarks;
    private String imagePath;
}
