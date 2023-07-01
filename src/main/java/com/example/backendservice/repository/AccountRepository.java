package com.example.backendservice.repository;

import com.example.backendservice.model.entity.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long>, AccountRepositoryCustom {
    Optional<AccountEntity> findAccountEntityByUserName(String phoneNumber);
}
