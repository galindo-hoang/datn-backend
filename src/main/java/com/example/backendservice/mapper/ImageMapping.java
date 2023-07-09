package com.example.backendservice.mapper;

import com.example.backendservice.model.dto.ImageDto;
import com.example.backendservice.model.entity.product.ImageEntity;

public class ImageMapping {
    static public ImageDto entityToDto(ImageEntity entity) {
        return ImageDto.builder()
                .format(entity.getFormat())
                .resourceType(entity.getResourceType())
                .url(entity.getUrl())
                .timeStamp(entity.getTimeStamp())
                .createAt(entity.getCreatedAt())
                .bytes(entity.getBytes())
                .width(entity.getWidth())
                .height(entity.getHeight())
                .build();
    }
}
