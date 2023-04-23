package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long>, IngredientRepositoryCustom {
}
