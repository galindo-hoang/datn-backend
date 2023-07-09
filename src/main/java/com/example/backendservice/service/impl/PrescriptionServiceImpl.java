package com.example.backendservice.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.example.backendservice.mapper.ImageMapping;
import com.example.backendservice.model.dto.ImageDto;
import com.example.backendservice.repository.ImageRepositoryCustom;
import com.example.backendservice.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {
    private final ImageRepositoryCustom imageRepositoryCustom;

    /**
     * type of sort_by ["public_id", "filename","type", "size", "created_at", "updated_at", "width", "height", "aspect_ratio","score"]
     * @param size
     * @param offset
     * @return
     */
    @Override
    public List<ImageDto> getListImage(Long size, Long offset) {
        Map options = ObjectUtils.asMap(
                "prefix", "prescription_images/",
                "type", "upload",
                "max_result", size,
                "offset", offset,
                "sort_by", "updated_at",
                "direction", -1
        );
        return imageRepositoryCustom.getImages(options).stream().map(ImageMapping::entityToDto).toList();
    }
}
