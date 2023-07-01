package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.TopSearchDrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopSearchRepository extends JpaRepository<TopSearchDrugEntity, Long> {
    Optional<TopSearchDrugEntity> findTopSearchDrugEntityByDrug_Id(Long id);
}
