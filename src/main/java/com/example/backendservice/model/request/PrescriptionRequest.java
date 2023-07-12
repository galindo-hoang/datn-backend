package com.example.backendservice.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionRequest {
    private String imageBase64;
    private Long id;
}
