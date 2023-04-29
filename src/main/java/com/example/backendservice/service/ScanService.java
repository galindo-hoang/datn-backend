package com.example.backendservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ScanService {
    String upload(MultipartFile image);
    Boolean download(String fileName);
}
