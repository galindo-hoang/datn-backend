package com.example.backendservice.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class CategoryRequest {
    Long id;
    String name;
    String imageBase64;
}
