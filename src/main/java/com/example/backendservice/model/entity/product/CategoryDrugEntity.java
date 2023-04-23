package com.example.backendservice.model.entity.product;


import com.example.backendservice.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category_drug")
public class CategoryDrugEntity extends BaseEntity {
    @EmbeddedId
    private CategoryDrugId id;

    @ManyToOne
    @MapsId("categoryId")
    private CategoryEntity category;

    @ManyToOne
    @MapsId("drugId")
    private DrugEntity drug;
}
