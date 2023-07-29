package com.example.backendservice.model.entity.feedback;

import com.example.backendservice.common.model.BaseEntity;
import com.example.backendservice.common.utils.DataAdapter;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "feedback")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackEntity extends BaseEntity implements DataAdapter<FeedbackEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String os;
    private String email;
    private String title;
    private String note;
    private String imagePath;
    private String description;
    private String issue;
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());

    @Override
    public FeedbackEntity merge(FeedbackEntity newData) {
        if (newData.getNote() != null && !newData.getNote().isBlank()) {
            this.setNote(newData.getNote());
        }
        return this;
    }
}
