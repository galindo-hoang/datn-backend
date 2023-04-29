package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.FileUtils;
import com.example.backendservice.model.request.ImageRequest;
import com.example.backendservice.repository.DrugRepository;
import com.example.backendservice.repository.ImageRepository;
import com.example.backendservice.service.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ScanServiceImpl implements ScanService {
    private final DrugRepository drugRepository;
    @Override
    public String upload(MultipartFile image) {
        ImageRequest imageRequest = ImageRequest.builder()
                .contentType(image.getContentType())
                .size(image.getSize())
                .extension(FileUtils.getExtension(Objects.requireNonNull(image.getOriginalFilename())))
                .multipartFile(image)
                .build();
        return drugRepository.uploadImage(imageRequest);
        // after apply module
    }

    @Override
    public Boolean download(String fileName) {
        return drugRepository.download(fileName);
    }

}
