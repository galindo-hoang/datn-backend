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
                    "cloud_name", "dscc9chrk",
                    "api_key", "683585168923456",
                    "api_secret", "LTr8lMano9zTFAXyzub0VPY08nQ",
                    "overwrite", true,
                    "format", "jpg"
            ));
        }
        return instance;
    }
}
