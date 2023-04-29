package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.CodeGeneratorUtils;
import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.common.utils.PhoneNumberUtils;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.AccountMapping;
import com.example.backendservice.model.dto.AuthDto;
import com.example.backendservice.model.entity.account.AccountEntity;
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
    private final AccountMapping accountMapper;
    private final HashMap<String, Pair<AuthDto, AccountRequest>> register = new HashMap<>();

    @Override
    public boolean checkingExist(String phoneNumber) {
        AccountEntity option = accountRepository.findUserByPhoneNumber(phoneNumber);
        return option != null;
    }

    @Override
    public boolean register(AccountRequest accountRequest) {
        if (PhoneNumberUtils.isValid(accountRequest.getPhoneNumber())) {
            if (checkingExist(accountRequest.getPhoneNumber())) return false;

            String otp = CodeGeneratorUtils.invoke();
            AuthDto authDto = new AuthDto();
            authDto.setOtp(otp);
            register.put(accountRequest.getPhoneNumber(), Pair.pair(authDto, accountRequest));
            return true;
        } else throw new ResourceInvalidException(Constants.PHONE + Constants.IN_VALID);
    }

    @Override
    public boolean validate(AccountRequest accountRequest) {
        if (register.containsKey(accountRequest.getPhoneNumber())) {
            AuthDto auth = register.get(accountRequest.getPhoneNumber()).first;
            AccountRequest account = register.get(accountRequest.getPhoneNumber()).second;
            if (!Objects.equals(auth.getOtp(), accountRequest.getOtp())) return false;
            AccountEntity user = accountMapper.requestToEntity(account);
            accountRepository.save(user);
            register.remove(accountRequest.getPhoneNumber());
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
    public AuthDto loginTraditional(AccountRequest accountRequest) {
        AccountEntity user = accountRepository.findUserByPhoneNumber(accountRequest.getPhoneNumber());
        if (user != null && user.getPassword().equals(accountRequest.getPassword())) {
            // TODO: do JWT
            return null;
        } else throw new ResourceNotFoundException(Constants.ACCOUNT + Constants.NOT_FOUND);
    }
}
