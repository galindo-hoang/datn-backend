package com.example.backendservice.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrugRequest {
    private String drugName;
    private String imageBase64;
    private Long price;
    private String registerNumber;
    private String activeIngredient;
    private String dosageForms;
    private String usageDosage;
    private String sideEffects;
    private String drugStorage;
    private String remarks;
    private String label;
    private Long categoryId;

    private Long id;
}
