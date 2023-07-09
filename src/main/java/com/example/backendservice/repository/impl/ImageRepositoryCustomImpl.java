package com.example.backendservice.repository.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.backendservice.common.utils.CloudinaryUtils;
import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.common.utils.FileUtils;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.model.entity.product.ImageEntity;
import com.example.backendservice.model.request.ImageRequest;
import com.example.backendservice.repository.ImageRepositoryCustom;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ImageRepositoryCustomImpl implements ImageRepositoryCustom {
    @Override
    public String uploadImage(ImageRequest imageRequest) {
        String fileName = ((Long) System.currentTimeMillis()).toString().concat(imageRequest.getExtension());
        File file = FileUtils.convertMultipartFileToFile(imageRequest.getMultipartFile(), fileName);                      // to convert multipartFile to File
        String TEMP_URL = this.uploadFile(file, fileName, imageRequest.getContentType());                                   // to get uploaded file link
        file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
        return TEMP_URL;
    }

    @Override
    public Boolean download(String fileName) {
        try {
            String destFileName = UUID.randomUUID().toString().concat(FileUtils.getExtension(fileName));     // to set random strinh for destination file name
            ////////////////////////////////   Download  ////////////////////////////////////////////////////////////////////////
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(Constants.PATH_JSON_FIREBASE));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            Blob blob = storage.get(BlobId.of(Constants.BUCKET_NAME, fileName));
            blob.downloadTo(Paths.get(destFileName));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadImageBase64Cloudinary(String fileBase64, String type, String fileName) throws IOException {
        if (fileBase64 == null || fileBase64.isBlank()) throw new ResourceInvalidException("");
        Map uploadImage = CloudinaryUtils.getInstance().uploader().upload(fileBase64, ObjectUtils.asMap(
                "public_id", type + "_" + fileName,
                "folder", type));
        return String.valueOf(uploadImage.get("secure_url"));

    }

    @Override
    public String uploadImageRemoteCloudinary(String url, String type, String fileName) throws IOException {
        if (url.isBlank()) throw new ResourceInvalidException("");
        Map uploadImage = CloudinaryUtils.getInstance().uploader().upload(new File(url), ObjectUtils.asMap(
                "public_id", type + "#" + fileName,
                "folder", type));
        return String.valueOf(uploadImage.get("secure_url"));
    }

    @Override
    public void deleteImageInCloudinary(String url) {

    }

    @Override
    public List<ImageEntity> getImages(Map options) {
        Cloudinary instance = CloudinaryUtils.getInstance();
        Gson gson = new Gson();
        try {
            ArrayList<Map<String, Object>> images = (ArrayList<Map<String, Object>>) instance.api().resources(options).values().stream().toList().get(0);
            return images.stream().map(image -> gson.fromJson(gson.toJson(image), ImageEntity.class)).toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String uploadFile(File file, String fileName, String extension) {
        try {
            BlobId blobId = BlobId.of(Constants.BUCKET_NAME, fileName);
            BlobInfo blocInfo = BlobInfo.newBuilder(blobId).setContentType(extension).build();
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(Constants.PATH_JSON_FIREBASE));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            return storage.create(blocInfo, Files.readAllBytes(file.toPath())).getMediaLink();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
