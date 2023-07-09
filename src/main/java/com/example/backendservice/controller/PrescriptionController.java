package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.ImageDto;
import com.example.backendservice.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("prescription")
@RequiredArgsConstructor
public class PrescriptionController extends BaseController {
    private final PrescriptionService prescriptionService;

    @GetMapping("multiple")
    public ResponseEntity<List<ImageDto>> getListImage(
            @RequestParam(required = false, defaultValue = "20") Long size,
            @RequestParam(required = false, defaultValue = "0") Long offset
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.getListImage(size, offset));
    }
}
