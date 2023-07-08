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
    private Long price;
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
    public DrugEntity merge(DrugEntity newData) {
        this.setDrugName(newData.getDrugName() != null ? newData.getDrugName() : this.getDrugName());
        this.setRegisterNumber(newData.getRegisterNumber() != null ? newData.getRegisterNumber() : this.getRegisterNumber());
        this.setDosageForm(newData.getDosageForm() != null ? newData.getDosageForm() : this.getDosageForm());
        this.setPrice(newData.getPrice() != null ? newData.getPrice() : this.getPrice());
        this.setUsageAndDosage(newData.getUsageAndDosage() != null ? newData.getUsageAndDosage() : this.getUsageAndDosage());
        this.setIndications(newData.getIndications() != null ? newData.getIndications() : this.getIndications());
        this.setContraindications(newData.getContraindications() != null ? newData.getContraindications() : this.getContraindications());
        this.setInteractions(newData.getInteractions() != null ? newData.getInteractions() : this.getInteractions());
        this.setSideEffects(newData.getSideEffects() != null ? newData.getSideEffects() : this.getSideEffects());
        this.setDrugStorage(newData.getDrugStorage() != null ? newData.getDrugStorage() : this.getDrugStorage());
        this.setRemarks(newData.getRemarks() != null ? newData.getRemarks() : this.getRemarks());
        this.setImagePath(newData.getImagePath() != null ? newData.getImagePath() : this.getImagePath());
        this.setLabel(newData.getLabel() != null ? newData.getLabel() : this.getLabel());
        return this;
    }
}
