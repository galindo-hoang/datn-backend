package com.example.backendservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LastUpload {
    Long monthYear;
    Long numberOfDrugs;
    Long totalRate;
}
