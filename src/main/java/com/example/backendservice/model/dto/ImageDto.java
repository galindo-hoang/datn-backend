package com.example.backendservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
   private String format;
   private String resourceType;
   private String url;
   private Long timeStamp;
   private String createAt;
   private Long bytes;
   private Long width;
   private Long height;
}
