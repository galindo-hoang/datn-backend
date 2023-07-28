package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.FeedbackMapper;
import com.example.backendservice.model.dto.FeedbackDto;
import com.example.backendservice.model.entity.feedback.FeedbackEntity;
import com.example.backendservice.model.request.FeedbackRequest;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.repository.FeedbackRepository;
import com.example.backendservice.repository.ImageRepositoryCustom;
import com.example.backendservice.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ImageRepositoryCustom imageRepository;

    @Override
    public FeedbackDto addFeedback(FeedbackRequest feedback) {
        FeedbackEntity feedbackEntity = FeedbackMapper.requestToEntity(feedback);
        feedbackEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        return FeedbackMapper.entityToDto(feedbackRepository.save(feedbackEntity));
    }

    @Override
    public List<FeedbackDto> getFeedbacks(FilterRequest filter) {
        return feedbackRepository.getListFeedback(
                "filter.getStartDate().toString()",
                "filter.getEndDate().toString()",
                filter.getOffset(),
                filter.getLimit(),
                filter.getTypeSort(),
                filter.getSort().equalsIgnoreCase("asc")
        ).stream().map(FeedbackMapper::entityToDto).toList();
    }


    @Override
    public FeedbackDto getFeedbackById(Long id) {
        FeedbackEntity feedback = feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feedback " + Constants.NOT_FOUND));
        return FeedbackMapper.entityToDto(feedback);
    }

    @Override
    public FeedbackDto updateFeedback(FeedbackRequest feedbackRequest) {
        FeedbackEntity feedback = feedbackRepository.findById(feedbackRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Feedback " + Constants.NOT_FOUND));
        FeedbackEntity request = FeedbackEntity.builder()
                .note(feedbackRequest.getNote())
                .build();
        feedback.merge(request);
        return FeedbackMapper.entityToDto(feedbackRepository.save(feedback));
    }

    @Override
    public Integer getSize() {
        return feedbackRepository.findAll().size();
    }
}
