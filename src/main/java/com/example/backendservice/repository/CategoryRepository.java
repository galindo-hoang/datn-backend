package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, CategoryRepositoryCustom {
    void deleteCategoryEntityByName(String name);
}
