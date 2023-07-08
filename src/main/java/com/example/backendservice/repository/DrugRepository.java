package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.DrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<DrugEntity, Long>, DrugRepositoryCustom {
    Optional<DrugEntity> findDrugEntityById(Long id);
}
