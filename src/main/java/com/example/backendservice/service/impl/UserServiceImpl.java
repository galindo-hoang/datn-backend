package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.CodeGeneratorUtils;
import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.common.utils.PhoneNumberUtils;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.mapper.AccountBuilder;
import com.example.backendservice.model.dto.AuthDto;
import com.example.backendservice.model.entity.account.UserEntity;
import com.example.backendservice.model.request.AccountRequest;
import com.example.backendservice.repository.AccountRepository;
import com.example.backendservice.service.UserService;
import graphql.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AccountRepository accountRepository;
    private final AccountBuilder accountBuilder;
    private final HashMap<String, Pair<AuthDto, AccountRequest>> register = new HashMap<>();

    @Override
    public boolean checkingExist(String phoneNumber) {
        UserEntity option = accountRepository.findUserByPhoneNumber(phoneNumber);
        return option != null;
    }

    @Override
    public boolean register(AccountRequest accountRequest) {
        if (PhoneNumberUtils.isValid(accountRequest.getPhoneNumber())) {
            if (checkingExist(accountRequest.getPhoneNumber())) return false;

            String otp = CodeGeneratorUtils.invoke();
            register.put(accountRequest.getPhoneNumber(), Pair.pair(new AuthDto(otp), accountRequest));
            return true;
        } else throw new ResourceInvalidException(Constants.PHONE + Constants.IN_VALID);
    }

    @Override
    public Boolean validate(AccountRequest accountRequest) {
        if (register.containsKey(accountRequest.getPhoneNumber())) {
            AuthDto auth = register.get(accountRequest.getPhoneNumber()).first;
            AccountRequest account = register.get(accountRequest.getPhoneNumber()).second;
            if (!Objects.equals(auth.getOtp(), accountRequest.getOtp())) return false;
            UserEntity user = accountBuilder.requestToEntity(account);
            accountRepository.save(user);
            return true;
        } else return false;
    }

    @Override
    public AuthDto forgetPassword(AccountRequest accountRequest) {
        return null;
    }

    @Override
    public AuthDto changePassword(AccountRequest accountRequest) {
        return null;
    }

    @Override
    public Boolean loginTraditional(AccountRequest accountRequest) {
        UserEntity user = accountRepository.findUserByPhoneNumber(accountRequest.getPhoneNumber());
        // TODO: do JWT
        if(user != null && user.getPassword().equals(accountRequest.getPassword())) {
            return true;
        }else return false;
    }
}
