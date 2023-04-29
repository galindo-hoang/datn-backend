package com.example.backendservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthDto {
    private String otp;

    // login
    private String refreshToken;
    private String accessToken;
}
