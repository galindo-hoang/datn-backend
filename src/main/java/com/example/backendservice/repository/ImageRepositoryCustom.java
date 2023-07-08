package com.example.backendservice.repository;

import com.example.backendservice.model.request.ImageRequest;

import java.io.IOException;

public interface ImageRepositoryCustom {
    String uploadImage(ImageRequest imageRequest);

    Boolean download(String fileName);

    String uploadImageBase64Cloudinary(String fileBase64, String type, String fileName) throws IOException;
    String uploadImageRemoteCloudinary(String url, String type, String fileName) throws IOException;
    void deleteImageInCloudinary(String url);

}
