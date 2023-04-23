package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.CategoryDrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDrugRepository extends JpaRepository<CategoryDrugEntity, Long> {
}
