package com.example.backendservice.repository;

import com.example.backendservice.model.entity.account.UserEntity;

public interface AccountRepositoryCustom {
    UserEntity findUserByPhoneNumber(String phoneNumber);
}
