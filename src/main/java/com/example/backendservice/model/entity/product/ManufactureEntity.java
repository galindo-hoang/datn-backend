package com.example.backendservice.model.entity.product;

import com.example.backendservice.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "manufacture")
public class ManufactureEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;

//    @OneToMany(mappedBy = "manufacture", cascade = CascadeType.ALL)
//    private List<DrugEntity> drugs;

//    public void addDrug(DrugEntity drug) {
//        this.drugs.add(drug);
//        drug.setManufacture(this);
//    }
//
//    public void removeDrug(DrugEntity drug) {
//        this.drugs.remove(drug);
//        drug.setManufacture(null);
//    }
}
