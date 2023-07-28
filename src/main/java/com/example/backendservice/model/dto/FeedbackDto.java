package com.example.backendservice.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private Long id;
    private String os;
    private String note;
    private String title;
    private Long createdOn;
    private String imagePath;
    private String description;
}
