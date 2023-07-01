package com.example.backendservice.repository.impl;

import com.example.backendservice.model.entity.account.AccountEntity;
import com.example.backendservice.repository.AccountRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public AccountEntity findUserByPhoneNumber(String phoneNumber) {
//        return null;
//    }
}
