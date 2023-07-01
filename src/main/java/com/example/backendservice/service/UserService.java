package com.example.backendservice.service;

import com.example.backendservice.model.dto.AuthDto;
import com.example.backendservice.model.request.AccountRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean checkingExist(String phoneNumber);

    boolean register(AccountRequest accountRequest);

    boolean validate(AccountRequest accountRequest);

    AuthDto forgetPassword(AccountRequest accountRequest);

    AuthDto changePassword(AccountRequest accountRequest);

    AuthDto loginTraditional(AccountRequest accountRequest);

    AuthDto refreshToken(String refreshToken);
}
