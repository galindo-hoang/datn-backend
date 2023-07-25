package com.example.backendservice.repository.impl;

import com.example.backendservice.common.model.SortType;
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
    public List<CategoryEntity> findCategoriesByText(String name, Long offset, Long size, SortType sortType, Boolean asc) {
        return new JPAQueryFactory(entityManager)
                .from(categoryEntity)
                .where(categoryEntity.name.toLowerCase().contains((name.isBlank() ? "" : name).toLowerCase()))
                .offset(offset).limit(size)
                .orderBy(getTypeSort(sortType, asc))
                .select(categoryEntity)
                .fetch();
    }

    @Override
    public List<CategoryEntity> findSizeByText(String name) {
        return new JPAQueryFactory(entityManager)
                .from(categoryEntity)
                .where(categoryEntity.name.toLowerCase().contains(name.toLowerCase()))
                .orderBy(categoryEntity.name.asc())
                .select(categoryEntity)
                .fetch();
    }

    private OrderSpecifier getTypeSort(SortType typeSort, Boolean asc) {
        if (typeSort.equals(SortType.ALPHABET)) {
            return asc ? categoryEntity.name.asc() : categoryEntity.name.desc();
        } else {
            return asc ? categoryEntity.lastModify.asc() : categoryEntity.lastModify.desc();
        }
    }
}
