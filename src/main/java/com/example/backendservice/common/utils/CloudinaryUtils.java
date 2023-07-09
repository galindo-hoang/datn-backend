package com.example.backendservice.common.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Synchronized;

public class CloudinaryUtils {
    private static Cloudinary instance;

    @Synchronized
    public static Cloudinary getInstance() {
        if (instance == null) {
            instance = new Cloudinary(ObjectUtils.asMap(
//                    "cloud_name", "dscc9chrk",
//                    "api_key", "683585168923456",
//                    "api_secret", "LTr8lMano9zTFAXyzub0VPY08nQ",
                    "cloud_name", "dg7yxwfmn",
                    "api_key", "135962415994417",
                    "api_secret", "KdsBKQfJ4AAgTpKgk6eZjyDDcms",
                    "overwrite", true,
                    "format", "jpg"
            ));
        }
        return instance;
    }
}
