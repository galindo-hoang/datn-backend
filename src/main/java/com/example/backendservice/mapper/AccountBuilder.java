package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.AccountDto;
import com.example.backendservice.model.entity.account.UserEntity;
import com.example.backendservice.model.request.AccountRequest;

public interface AccountBuilder {
    class Builder {
        private String phoneNumber;
        private String password;
    }
    UserEntity requestToEntity(AccountRequest request);
    AccountDto entityToDto(UserEntity entity);
}
