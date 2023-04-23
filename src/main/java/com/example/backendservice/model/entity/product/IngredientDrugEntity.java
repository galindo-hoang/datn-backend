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
@Table(name = "ingredient_drug")
public class IngredientDrugEntity extends BaseEntity {
    @EmbeddedId
    private IngredientDrugId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    private IngredientEntity ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("drugId")
    private DrugEntity drug;

    private String unit;
    private Double numberUnit;

}
