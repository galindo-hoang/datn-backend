package com.example.backendservice.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackRequest {
    private Long id;
    private String title;
    private String description;
    private String imageBase64;
    private String note;
    private String os;
    private String issue;
    private String email;
}
