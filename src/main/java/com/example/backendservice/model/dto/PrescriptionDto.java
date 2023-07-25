package com.example.backendservice.model.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
    private Long id;
    private Long createdOn;
    private String imagePath;
    private Long bytes;
    private Long width;
    private Long height;

    private Long rate;
    private String review;
}
