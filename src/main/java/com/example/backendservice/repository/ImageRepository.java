package com.example.backendservice.repository;

import com.example.backendservice.model.request.ImageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository {
    String uploadImage(ImageRequest imageRequest);
    Boolean download(String fileName);
}
