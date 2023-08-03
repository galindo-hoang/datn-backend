package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.DetailRate;
import com.example.backendservice.model.dto.LastUpload;
import com.example.backendservice.model.dto.PrescriptionDto;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.model.request.PrescriptionRequest;
import com.example.backendservice.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("prescription")
@RequiredArgsConstructor
public class PrescriptionController extends BaseController {
    private final PrescriptionService prescriptionService;


    @GetMapping
    public ResponseEntity<PrescriptionDto> getDetails(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.getById(id));
    }

    @RequestMapping(value = "add", method = POST)
    public ResponseEntity<PrescriptionDto> addPrescription(@RequestBody PrescriptionRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(prescriptionService.addPrescription(request));
    }

    @GetMapping("multiple")
    public ResponseEntity<List<PrescriptionDto>> getListPrescription(
            @RequestParam(required = false, defaultValue = "20") Long size,
            @RequestParam(required = false, defaultValue = "0") Long offset,
            @RequestParam(required = false, defaultValue = "asc") String sort
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.getListImage(
                FilterRequest.builder()
                        .offset(offset)
                        .limit(size)
                        .sort(sort)
                        .build()
        ));
    }

    @RequestMapping(value = "delete", method = DELETE)
    public ResponseEntity<Objects> deletePrescription(@RequestBody PrescriptionRequest request) {
        prescriptionService.deletePrescription(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping(path = "lastUpdate")
    ResponseEntity<List<LastUpload>> findLastUpdate(
            @RequestParam Long startYear,
            @RequestParam Long startMonth,
            @RequestParam Long endYear,
            @RequestParam Long endMonth
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.listLastUpload(startYear, startMonth, endYear, endMonth));
    }


    @GetMapping(path = "month/detail")
    ResponseEntity<DetailRate> analyzeMonth(@RequestParam Integer month) {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.analyzeRate(month));
    }

    @GetMapping("size")
    ResponseEntity<Long> getSize() {
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.getSize());
    }

}
