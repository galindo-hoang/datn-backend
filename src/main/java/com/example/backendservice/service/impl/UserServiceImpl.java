package com.example.backendservice.service.impl;

import com.example.backendservice.common.model.UserRole;
import com.example.backendservice.common.utils.CodeGeneratorUtils;
import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.common.utils.JwtTokenUtil;
import com.example.backendservice.common.utils.PhoneNumberUtils;
import com.example.backendservice.exception.ForbiddenException;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.AccountMapping;
import com.example.backendservice.model.dto.AuthDto;
import com.example.backendservice.model.entity.account.AccountEntity;
import com.example.backendservice.model.request.AccountRequest;
import com.example.backendservice.repository.AccountRepository;
import com.example.backendservice.service.UserService;
import graphql.util.Pair;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AccountRepository accountRepository;
    private final AccountMapping accountMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final HashMap<String, Pair<AuthDto, AccountRequest>> register = new HashMap<>();

    @PostConstruct
    private void addAdmin() {
        accountRepository.save(AccountEntity.builder()
                .userName("admin")
                .password("admin")
                .phoneNumber("999999999")
                .createdOn(new Timestamp(System.currentTimeMillis()))
                .userRole(UserRole.ROLE_ADMIN)
                .birthday(new Timestamp(System.currentTimeMillis()))
                .imagePath("https://tuyengiao.vn/ban-can-biet/ve-viec-su-dung-dang-ky-dang-cong-san-viet-nam-143374")
                .build()
        );
    }

    @Override
    public boolean checkingExist(String userName) {
        Optional<AccountEntity> option = accountRepository.findAccountEntityByUserName(userName);
        return option.isPresent();
    }

    @Override
    public boolean register(AccountRequest accountRequest) {
        if (PhoneNumberUtils.isValid(accountRequest.getPhoneNumber())) {
            if (checkingExist(accountRequest.getUserName())) return false;

            String otp = CodeGeneratorUtils.invoke();
            AuthDto authDto = new AuthDto();
//            authDto.setOtp(otp);
            register.put(accountRequest.getUserName(), Pair.pair(authDto, accountRequest));
            return true;
        } else throw new ResourceInvalidException(Constants.PHONE + Constants.IN_VALID);
    }

    @Override
    public boolean validate(AccountRequest accountRequest) {
        if (register.containsKey(accountRequest.getUserName())) {
            AuthDto auth = register.get(accountRequest.getUserName()).first;
            AccountRequest account = register.get(accountRequest.getUserName()).second;
//            if (!Objects.equals(auth.getOtp(), accountRequest.getOtp())) return false;
            AccountEntity user = accountMapper.requestToEntity(account);
            accountRepository.save(user);
            register.remove(accountRequest.getUserName());
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
        Optional<AccountEntity> optionUser = accountRepository.findAccountEntityByUserName(accountRequest.getUserName());
        if (optionUser.isPresent() && optionUser.get().getPassword().equals(accountRequest.getPassword())) {
            AccountEntity user = optionUser.get();
            String accessToken = jwtTokenUtil.generateToken(user.getUserName(), JwtTokenUtil.JWT_ACCESS_TOKEN_VALIDITY);
            String refreshToken = jwtTokenUtil.generateToken(user.getUserName(), JwtTokenUtil.JWT_REFRESH_TOKEN_VALIDITY);
            return AuthDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        } else throw new ResourceNotFoundException(Constants.ACCOUNT + Constants.NOT_FOUND);
    }

    @Override
    public AuthDto refreshToken(String refreshToken) {
        try {
            String dataFromToken = jwtTokenUtil.getUserNameFromToken(refreshToken);
            AccountEntity accountEntity = accountRepository.findAccountEntityByUserName(dataFromToken).orElseThrow(() -> new ResourceNotFoundException("refreshToken " + Constants.IN_VALID));
            if (jwtTokenUtil.validateToken(refreshToken, accountEntity.getUserName())) {
                String accessToken = jwtTokenUtil.generateToken(accountEntity.getUserName(), JwtTokenUtil.JWT_ACCESS_TOKEN_VALIDITY);
                return AuthDto.builder()
                        .refreshToken(refreshToken)
                        .accessToken(accessToken)
                        .build();
            } else {
                throw new ForbiddenException(Constants.TOKEN + Constants.IN_VALID);
            }
        } catch (Exception e) {
            throw new ResourceInvalidException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountEntity> accountEntity = accountRepository.findAccountEntityByUserName(username);
        if(accountEntity.isPresent()) {
            return new User(accountEntity.get().getUserName(),"", new ArrayList<>());
        } else throw new ForbiddenException(Constants.TOKEN + Constants.IN_VALID);
    }
}
