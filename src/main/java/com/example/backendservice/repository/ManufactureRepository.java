package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.ManufactureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufactureRepository extends JpaRepository<ManufactureEntity, Long> {
}
