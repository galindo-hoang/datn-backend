package com.example.backendservice.repository;

import com.example.backendservice.model.entity.account.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<UserEntity, Long>, AccountRepositoryCustom {
}
