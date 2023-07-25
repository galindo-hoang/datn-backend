package com.example.backendservice.model.entity.prescription;

import com.example.backendservice.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
@Entity
@Table(name = "prescription")
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());
    private String imagePath;
    private Long bytes;
    private Long width;
    private Long height;

    private Long rate;
    private String review;

}
