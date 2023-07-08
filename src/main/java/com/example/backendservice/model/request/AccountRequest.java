package com.example.backendservice.model.request;

import com.example.backendservice.common.utils.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private Genre genre;
    private Date birthdate;

    private String otp;

    private String imageUrl;

    private String refreshToken;
}
