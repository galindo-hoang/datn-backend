package com.example.backendservice.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthDto {
//    private String otp;

    // login
    private String refreshToken;
    private String accessToken;
}
