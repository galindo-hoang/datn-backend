package com.example.backendservice.repository.impl;

import com.example.backendservice.model.entity.prescription.PrescriptionEntity;
import com.example.backendservice.repository.PrescriptionRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.example.backendservice.model.entity.prescription.QPrescriptionEntity.prescriptionEntity;

@Repository
public class PrescriptionRepositoryCustomImpl implements PrescriptionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PrescriptionEntity> findTopUpload(Long offset, Long size, Boolean asc) {
        return new JPAQueryFactory(entityManager)
                .from(prescriptionEntity)
                .offset(offset).limit(size)
                .orderBy(asc ? prescriptionEntity.createdOn.asc() : prescriptionEntity.createdOn.desc())
                .select(prescriptionEntity)
                .fetch();
    }

    @Override
    public List<Tuple> analyzePrescription(String startDate, String endDate) {

        Expression<String> yearMonth = prescriptionEntity.createdOn.year().stringValue().concat("-").concat(prescriptionEntity.createdOn.month().stringValue());

        return new JPAQueryFactory(entityManager)
                .from(prescriptionEntity)
                .where(prescriptionEntity.createdOn.goe(Timestamp.valueOf(startDate))
                        .and(prescriptionEntity.createdOn.lt(Timestamp.valueOf(endDate))))
                .groupBy(prescriptionEntity.createdOn.year(), prescriptionEntity.createdOn.month())
                .select(yearMonth, prescriptionEntity.count())
                .fetch();
    }
}
