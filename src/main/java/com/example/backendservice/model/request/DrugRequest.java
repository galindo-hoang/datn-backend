package com.example.backendservice.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrugRequest {
    private String drugName;
    private String image;
    private String registerNumber;
    private String activeIngredient;
    private String dosageForms;
    private String usageDosage;
    private String sideEffects;
    private String drugStorage;
    private String remarks;
    private String label;

    private Long id;
}
