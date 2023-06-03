package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.service.DrugService;
import com.example.backendservice.service.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("scan")
@RequiredArgsConstructor
public class ScanController extends BaseController {
    private final DrugService drugService;
    private final ScanService scanService;

    @RequestMapping(path = "upload", method = POST)
    ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).body(scanService.upload(image));
    }

    @GetMapping(path = "download")
    ResponseEntity<Boolean> download(@RequestParam String fileName) {
        return ResponseEntity.status(HttpStatus.OK).body(scanService.download(fileName));
    }
}
