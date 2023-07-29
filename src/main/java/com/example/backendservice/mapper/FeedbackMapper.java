package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.FeedbackDto;
import com.example.backendservice.model.entity.feedback.FeedbackEntity;
import com.example.backendservice.model.request.FeedbackRequest;

public class FeedbackMapper {
    public static FeedbackDto entityToDto(FeedbackEntity feedbackEntity) {
        return FeedbackDto.builder()
                .id(feedbackEntity.getId())
                .createdOn(feedbackEntity.getCreatedOn().getTime())
                .description(feedbackEntity.getDescription())
                .title(feedbackEntity.getTitle())
                .imagePath(feedbackEntity.getImagePath())
                .note(feedbackEntity.getNote())
                .os(feedbackEntity.getOs())
                .email(feedbackEntity.getEmail())
                .issue(feedbackEntity.getIssue())
                .build();
    }

    public static FeedbackEntity requestToEntity(FeedbackRequest request) {
        return FeedbackEntity.builder()
                .title(request.getTitle())
                .os(request.getOs())
                .email(request.getEmail())
                .issue(request.getIssue())
                .description(request.getDescription())
                .build();
    }
}
