package com.example.backendservice.repository.impl;

import com.example.backendservice.common.model.SortType;
import com.example.backendservice.model.entity.feedback.FeedbackEntity;
import com.example.backendservice.repository.FeedbackRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.backendservice.model.entity.feedback.QFeedbackEntity.feedbackEntity;

@Repository
public class FeedbackRepositoryCustomImpl implements FeedbackRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FeedbackEntity> getListFeedback(
            String startDate,
            String endDate,
            Long offset,
            Long limit,
            SortType typeSort,
            Boolean asc
    ) {
        return new JPAQueryFactory(entityManager)
                .from(feedbackEntity)
                .offset(offset).limit(limit)
                .orderBy(asc ? feedbackEntity.createdOn.asc() : feedbackEntity.createdOn.desc())
                .select(feedbackEntity)
                .fetch();
    }
}
