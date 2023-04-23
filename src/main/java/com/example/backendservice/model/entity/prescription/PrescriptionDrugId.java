package com.example.backendservice.model.entity.prescription;

import com.example.backendservice.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDrugId implements Serializable {
    @Column(name = "prescription_id")
    private Long prescriptionId;

    @Column(name = "drugId")
    private Long drugId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrescriptionDrugId that)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(prescriptionId, that.prescriptionId))
            return false;
        return Objects.equals(drugId, that.drugId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (prescriptionId != null ? prescriptionId.hashCode() : 0);
        result = 31 * result + (drugId != null ? drugId.hashCode() : 0);
        return result;
    }
}
