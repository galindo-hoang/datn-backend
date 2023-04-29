package com.example.backendservice.model.entity.reminder;

import com.example.backendservice.common.model.BaseEntity;
import com.example.backendservice.model.entity.account.AccountEntity;
import com.example.backendservice.model.entity.prescription.PrescriptionDrugEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "reminder")
@NoArgsConstructor
@AllArgsConstructor
public class ReminderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private Long timeDuration;
    private Double beforeMinus;
    private Double interval;
    private Boolean active;
    @OneToMany(mappedBy = "reminder", cascade = CascadeType.ALL)
    private List<PrescriptionDrugEntity> prescriptionDrugs;
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity user;


    public void addSelf(PrescriptionDrugEntity prescriptionDrug, AccountEntity user) {
        prescriptionDrug.setReminder(this);
        prescriptionDrugs.add(prescriptionDrug);
        user.getReminders().add(this);
        this.user = user;
    }

    public void removeSelf(AccountEntity user) {
        while (!prescriptionDrugs.isEmpty()) {
            PrescriptionDrugEntity tmp = prescriptionDrugs.get(0);
            prescriptionDrugs.remove(0);
            tmp.setReminder(null);
        }
        user.getReminders().remove(this);
        this.user = null;
    }

}
