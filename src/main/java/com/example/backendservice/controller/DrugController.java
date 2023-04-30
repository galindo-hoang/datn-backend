package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.request.DrugRequest;
import com.example.backendservice.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("drug")
@RequiredArgsConstructor
public class DrugController extends BaseController {
    private final DrugService drugService;

    @RequestMapping(path = "add", method = POST)
    ResponseEntity<DrugDto> addDrug(DrugRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(drugService.addDrug(request));
    }

    @RequestMapping(path = "update", method = PUT)
    ResponseEntity<DrugDto> updateDrug(DrugRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(drugService.updateDrug(request));
    }

    @GetMapping()
    ResponseEntity<Long> getDrugById(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(id);
//        return ResponseEntity.status(HttpStatus.OK).body(drugService.findDrugById(id));
    }

    @RequestMapping(path = "delete", method = DELETE)
    ResponseEntity<Object> deleteDrug(@RequestParam Long id) {
        drugService.removeDrug(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping()
    ResponseEntity<List<DrugDto>> getListDrugs(
            @RequestParam String text,
            @RequestParam Long categoryId,
            @RequestParam Long offset,
            @RequestParam Long limit
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(drugService.findDrugsByFilter(
                        Map.of(
                                "text", text,
                                "categoryId", categoryId,
                                "offset", offset,
                                "limit", limit
                        ))
                );
    }
}
