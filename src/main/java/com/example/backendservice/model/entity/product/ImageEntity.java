package com.example.backendservice.model.entity.product;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @SerializedName("asset_id")
    private String assetId;
    private Long bytes;
    @SerializedName("created_at")
    private String createdAt;
    private String folder;
    private String format;
    private Long height;
    @SerializedName("public_id")
    private String publicId;
    @SerializedName("resource_type")
    private String resourceType;
    @SerializedName("secure_url")
    private String secureUrl;
    private String type;
    private String url;
    @SerializedName("version")
    private Long timeStamp;
    private Long width;
}