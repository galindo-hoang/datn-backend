package com.example.backendservice.model.entity.prescription;

import com.example.backendservice.common.model.BaseEntity;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.entity.reminder.ReminderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescription_drug")
public class PrescriptionDrugEntity extends BaseEntity {
    @EmbeddedId
    private PrescriptionDrugId id;
    private Long quantity;
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("prescriptionId")
    private PrescriptionEntity prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("drugId")
    private DrugEntity drug;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReminderEntity reminder;




}
