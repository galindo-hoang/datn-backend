package com.example.backendservice.repository;

import com.example.backendservice.common.model.SortType;
import com.example.backendservice.model.entity.feedback.FeedbackEntity;

import java.util.List;

public interface FeedbackRepositoryCustom {
    List<FeedbackEntity> getListFeedback(
            String startDate,
            String endDate,
            Long offset,
            Long limit,
            SortType typeSort,
            Boolean asc
    );
}
