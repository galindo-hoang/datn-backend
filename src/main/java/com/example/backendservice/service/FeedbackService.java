package com.example.backendservice.service;

import com.example.backendservice.model.dto.FeedbackDto;
import com.example.backendservice.model.request.FeedbackRequest;
import com.example.backendservice.model.request.FilterRequest;

import java.util.List;

public interface FeedbackService {
    FeedbackDto addFeedback(FeedbackRequest feedback);

    List<FeedbackDto> getFeedbacks(FilterRequest filter);

    FeedbackDto getFeedbackById(Long id);

    FeedbackDto updateFeedback(FeedbackRequest feedbackRequest);

    Integer getSize();
}
