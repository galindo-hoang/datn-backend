package com.example.backendservice.repository.impl;

import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.repository.CategoryRepositoryCustom;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.backendservice.model.entity.product.QCategoryEntity.categoryEntity;

@Repository
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<CategoryEntity> findCategoriesByText(String name, Long offset, Long size) {
        return new JPAQueryFactory(entityManager)
                .from(categoryEntity)
                .where(categoryEntity.name.contains(name.isBlank() ? name : ""))
                .offset(offset).limit(size).orderBy(categoryEntity.name.asc())
                .select(categoryEntity)
                .fetch();
    }

    @Override
    public List<CategoryEntity> findSizeByText(String name) {
        return new JPAQueryFactory(entityManager)
                .from(categoryEntity)
                .where(categoryEntity.name.contains(name))
                .orderBy(categoryEntity.name.asc())
                .select(categoryEntity)
                .fetch();
    }
}
