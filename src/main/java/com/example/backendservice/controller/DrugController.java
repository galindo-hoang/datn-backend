package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.request.DrugRequest;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("drug")
@RequiredArgsConstructor
public class DrugController extends BaseController {
    private final DrugService drugService;

    @RequestMapping(path = "add", method = POST)
    ResponseEntity<DrugDto> addDrug(@RequestBody DrugRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(drugService.addDrug(request));
    }

    @RequestMapping(path = "update", method = PUT)
    ResponseEntity<DrugDto> updateDrug(@RequestBody DrugRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(drugService.updateDrug(request));
    }

    @GetMapping()
    ResponseEntity<DrugDto> getDrugById(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(drugService.findDrugById(id));
    }

    @GetMapping(path = "size")
    ResponseEntity<Long> getSize(@RequestParam(required = false, defaultValue = "") String text) {
        return ResponseEntity.status(HttpStatus.OK).body(drugService.getSize(text));
    }

    @RequestMapping(path = "delete", method = DELETE)
    ResponseEntity<Object> deleteDrug(@RequestParam Long id) {
        drugService.removeDrug(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping(path = "multiple")
    ResponseEntity<List<DrugDto>> getListDrugsByName(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") Long offset,
            @RequestParam(required = false, defaultValue = "20") Long size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                drugService.findDrugsByText(
                        FilterRequest.builder()
                                .keyRequestText(name)
                                .offset(offset)
                                .limit(size)
                                .build()
                ));
    }

    @GetMapping(path = "category")
    ResponseEntity<List<DrugDto>> getListDrugsByCategory(
            @RequestParam String categoryName,
            @RequestParam(required = false, defaultValue = "0") Long offset,
            @RequestParam(required = false, defaultValue = "20") Long size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                drugService.findDrugsByCategory(
                        FilterRequest.builder()
                                .keyRequestText(categoryName)
                                .offset(offset)
                                .limit(size)
                                .build()
                ));
    }
}
