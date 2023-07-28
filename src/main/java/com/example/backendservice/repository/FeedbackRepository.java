package com.example.backendservice.repository;

import com.example.backendservice.model.entity.feedback.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>, FeedbackRepositoryCustom {
}
