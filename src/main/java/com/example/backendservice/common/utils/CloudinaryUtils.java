package com.example.backendservice.common.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Synchronized;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CloudinaryUtils {
    private static Cloudinary instance;

    @Synchronized
    public static Cloudinary getInstance() {
        if (instance == null) {
            instance = new Cloudinary(ObjectUtils.asMap(
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
