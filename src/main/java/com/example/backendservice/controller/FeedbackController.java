package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.common.model.SortType;
import com.example.backendservice.model.dto.FeedbackDto;
import com.example.backendservice.model.request.FeedbackRequest;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("feedback")
@RequiredArgsConstructor
public class FeedbackController extends BaseController {
    private final FeedbackService feedbackService;

    @RequestMapping(path = "add", method = POST)
    public ResponseEntity<FeedbackDto> addFeedback(@RequestBody FeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(feedbackService.addFeedback(request));
    }

    @RequestMapping(path = "update", method = POST)
    public ResponseEntity<FeedbackDto> modifyFeedback(@RequestBody FeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(feedbackService.updateFeedback(request));
    }

    @GetMapping(path = "multiple")
    public ResponseEntity<List<FeedbackDto>> getListFeedback(
            @RequestParam(required = false, defaultValue = "30") Long size,
            @RequestParam(required = false, defaultValue = "0") Long offset,
            @RequestParam(required = false, defaultValue = "date") String sortType,
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false) Long endDate,
            @RequestParam(required = false) Long startDate
    ) {
        FilterRequest request = FilterRequest.builder()
                .limit(size <= 0 ? 30 : size)
                .offset(offset)
                .typeSort(SortType.ALPHABET.name().equalsIgnoreCase(sortType) ? SortType.ALPHABET : SortType.DATE)
                .sort(sort)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getFeedbacks(request));
    }

    @GetMapping
    public ResponseEntity<FeedbackDto> getDetails(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getFeedbackById(id));
    }

    @GetMapping(path = "size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getSize());
    }
}
