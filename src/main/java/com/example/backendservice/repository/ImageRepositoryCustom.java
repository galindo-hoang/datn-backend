package com.example.backendservice.repository;

import com.example.backendservice.model.entity.product.ImageEntity;
import com.example.backendservice.model.request.ImageRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ImageRepositoryCustom {
    String uploadImage(ImageRequest imageRequest);

    Boolean download(String fileName);

    Map uploadImageBase64Cloudinary(String fileBase64, String type, String fileName) throws IOException;

    String uploadImageRemoteCloudinary(String url, String type, String fileName) throws IOException;

    boolean deleteFolder(String folder);

    boolean deleteImage(String folder, String fileName);

}
