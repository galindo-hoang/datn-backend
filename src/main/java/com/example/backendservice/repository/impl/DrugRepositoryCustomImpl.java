package com.example.backendservice.repository.impl;

import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.repository.DrugRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.backendservice.model.entity.product.QCategoryEntity.categoryEntity;
import static com.example.backendservice.model.entity.product.QDrugEntity.drugEntity;

@Repository
public class DrugRepositoryCustomImpl implements DrugRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DrugEntity> findDrugsByText(String text, Long offset, Long limit) {
        return new JPAQueryFactory(entityManager)
                .from(drugEntity)
                .where(drugEntity.drugName.toLowerCase().contains(text.toLowerCase()))
                .offset(offset).limit(limit)
                .orderBy(drugEntity.drugName.asc())
                .select(drugEntity)
                .fetch();
    }

    @Override
    public List<DrugEntity> findAllDrugsByText(String text) {
        return new JPAQueryFactory(entityManager)
                .from(drugEntity)
                .where(drugEntity.drugName.toLowerCase().contains(text.toLowerCase()))
                .orderBy(drugEntity.drugName.asc())
                .select(drugEntity)
                .fetch();
    }

    @Override
    public List<DrugEntity> findDrugsByCategory(String categoryName, Long offset, Long size) {
        return new JPAQueryFactory(entityManager)
                .from(drugEntity).join(categoryEntity).on(drugEntity.category.id.eq(categoryEntity.id))
                .where(drugEntity.category.name.toLowerCase().contains(categoryName.toLowerCase()))
                .offset(offset).limit(size)
                .orderBy(drugEntity.drugName.asc())
                .select(drugEntity)
                .fetch();
    }
}
