package com.example.backendservice.model.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ImageRequest {
    MultipartFile multipartFile;
    String contentType;
    String extension;
    Long size;
}
