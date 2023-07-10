package com.example.backendservice.repository;

import com.example.backendservice.model.entity.prescription.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long>, PrescriptionRepositoryCustom {
}
