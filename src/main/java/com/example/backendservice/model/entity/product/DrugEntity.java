package com.example.backendservice.model.entity.product;

import com.example.backendservice.common.model.BaseEntity;
import com.example.backendservice.common.utils.DataAdapter;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "drug")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrugEntity extends BaseEntity implements DataAdapter<DrugEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String drugName;
    private String registerNumber;
    private String dosageForm;
    private String usageAndDosage;
    private String indications;
    private String contraindications;
    private String interactions; // activate-Ingredient
    private String sideEffects;
    private Timestamp lastModify = new Timestamp(System.currentTimeMillis());
    private String label;
    private String drugStorage;
    private String remarks;
    private String imagePath;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private ManufactureEntity manufacture;
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    public void addSelf(
//            ManufactureEntity manufacture,
            CategoryEntity category
    ) {
//        manufacture.getDrugs().add(this);
//        this.manufacture = manufacture;
        category.getDrugs().add(this);
        this.category = category;
    }

    public void removeSelf(
//            ManufactureEntity manufacture,
            CategoryEntity category
    ) {
//        manufacture.getDrugs().remove(this);
        category.getDrugs().remove(this);
    }

    @Override
    public DrugEntity merge(DrugEntity oldData, DrugEntity newData) {
        oldData.setDrugName(newData.getDrugName() != null ? newData.getDrugName() : oldData.getDrugName());
        oldData.setRegisterNumber(newData.getRegisterNumber() != null ? newData.getRegisterNumber() : oldData.getRegisterNumber());
        oldData.setDosageForm(newData.getDosageForm() != null ? newData.getDosageForm() : oldData.getDosageForm());
        oldData.setUsageAndDosage(newData.getUsageAndDosage() != null ? newData.getUsageAndDosage() : oldData.getUsageAndDosage());
        oldData.setIndications(newData.getIndications() != null ? newData.getIndications() : oldData.getIndications());
        oldData.setContraindications(newData.getContraindications() != null ? newData.getContraindications() : oldData.getContraindications());
        oldData.setInteractions(newData.getInteractions() != null ? newData.getInteractions() : oldData.getInteractions());
        oldData.setSideEffects(newData.getSideEffects() != null ? newData.getSideEffects() : oldData.getSideEffects());
        oldData.setDrugStorage(newData.getDrugStorage() != null ? newData.getDrugStorage() : oldData.getDrugStorage());
        oldData.setRemarks(newData.getRemarks() != null ? newData.getRemarks() : oldData.getRemarks());
        oldData.setImagePath(newData.getImagePath() != null ? newData.getImagePath() : oldData.getImagePath());
        oldData.setLabel(newData.getLabel() != null ? newData.getLabel() : oldData.getLabel());
        return oldData;
    }
}
