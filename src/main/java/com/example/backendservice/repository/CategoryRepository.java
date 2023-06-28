package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, CategoryRepositoryCustom {
    void deleteCategoryEntityByName(String name);
    Optional<CategoryEntity> findCategoryEntityByName(String name);
}
