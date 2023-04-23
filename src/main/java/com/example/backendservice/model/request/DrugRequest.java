package com.example.backendservice.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrugRequest {
    private String drugName;
    private String registerNumber;
    private String activeIngredient;
    private String DosageForms;
    private String UsageDosage;
    private String sideEffects;
    private String drugStorage;
    private String remarks;

    private Long id;
}
