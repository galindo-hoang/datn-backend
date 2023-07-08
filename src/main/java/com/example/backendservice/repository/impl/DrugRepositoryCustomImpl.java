package com.example.backendservice.repository.impl;

import com.example.backendservice.common.model.SortType;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.repository.DrugRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.example.backendservice.model.entity.product.QCategoryEntity.categoryEntity;
import static com.example.backendservice.model.entity.product.QDrugEntity.drugEntity;
import static com.example.backendservice.model.entity.product.QTopSearchDrugEntity.topSearchDrugEntity;

@Repository
public class DrugRepositoryCustomImpl implements DrugRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DrugEntity> findDrugsByText(String text, Long offset, Long limit, SortType typeSort, Boolean asc) {
        return new JPAQueryFactory(entityManager)
                .from(drugEntity)
                .where(drugEntity.drugName.toLowerCase().contains(text.toLowerCase()))
                .offset(offset).limit(limit)
                .orderBy(getTypeSort(typeSort, asc))
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
    public List<DrugEntity> findDrugsByCategory(String categoryName, Long offset, Long size, SortType typeSort, Boolean asc) {
        return new JPAQueryFactory(entityManager)
                .from(drugEntity).join(categoryEntity).on(drugEntity.category.id.eq(categoryEntity.id))
                .where(drugEntity.category.name.toLowerCase().contains(categoryName.toLowerCase()))
                .offset(offset).limit(size)
                .orderBy(getTypeSort(typeSort, asc))
                .select(drugEntity)
                .fetch();
    }

    @Override
    public List<Tuple> findTopSearch(Long offset, Long size, Boolean asc) {
        return new JPAQueryFactory(entityManager)
                .from(drugEntity).join(topSearchDrugEntity).on(drugEntity.id.eq(topSearchDrugEntity.drug.id))
                .offset(offset).limit(size)
                .orderBy(asc ? topSearchDrugEntity.count.asc() : topSearchDrugEntity.count.desc())
                .select(drugEntity, topSearchDrugEntity.count, topSearchDrugEntity.lastSearch)
                .fetch();
    }

    @Override
    public List<Tuple> findLastUpdate(String startDate, String endDate) {
        Expression<String> yearMonth = drugEntity.lastModify.year().stringValue().concat("-").concat(drugEntity.lastModify.month().stringValue());

        List<Tuple> results = new JPAQueryFactory(entityManager)
                .from(drugEntity)
                .where(drugEntity.lastModify.goe(Timestamp.valueOf(startDate))
                        .and(drugEntity.lastModify.lt(Timestamp.valueOf(endDate))))
                .groupBy(drugEntity.lastModify.year(), drugEntity.lastModify.month())
                .select(yearMonth, drugEntity.count())
                .fetch();
        return results;
    }

    private OrderSpecifier getTypeSort(SortType typeSort, Boolean asc) {
        OrderSpecifier parsedSort;
        if (typeSort.equals(SortType.ALPHABET)) {
            parsedSort = asc ? drugEntity.drugName.asc() : drugEntity.drugName.desc();
        } else {
            parsedSort = asc ? drugEntity.lastModify.asc() : drugEntity.lastModify.desc();
        }
        return parsedSort;
    }

}
