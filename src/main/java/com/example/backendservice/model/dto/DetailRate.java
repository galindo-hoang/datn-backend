package com.example.backendservice.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DetailRate {
    private Long totalRate;
    private Long monthYear;
    private Map<String, Long> star;
}
