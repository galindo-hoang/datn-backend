package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.AccountDto;
import com.example.backendservice.model.entity.account.AccountEntity;
import com.example.backendservice.model.request.AccountRequest;
import org.springframework.stereotype.Component;

@Component
public class AccountMapping implements RequestMapping<AccountEntity, AccountRequest>, EntityMapping<AccountDto, AccountEntity>{
    @Override
    public AccountEntity requestToEntity(AccountRequest request) {
        return null;
    }

    @Override
    public AccountDto entityToDto(AccountEntity entity) {
        return null;
    }
}
