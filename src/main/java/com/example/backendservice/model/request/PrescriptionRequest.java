package com.example.backendservice.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionRequest {
    private String imageBase64;
    private Long id;
    private Long rate;
    private String review;

    private List<Long> months;
}
