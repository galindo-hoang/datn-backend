package com.example.backendservice.model.entity.prescription;

import com.example.backendservice.common.model.BaseEntity;
import com.example.backendservice.model.entity.account.AccountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "prescription")
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());
    private String note;
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity user;

    public void addSelf(AccountEntity user) {
        user.getPrescriptions().add(this);
        this.user = user;
    }

    public void removeSelf(AccountEntity user) {
        user.getPrescriptions().remove(this);
    }
}
