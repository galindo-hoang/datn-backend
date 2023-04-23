package com.example.backendservice.model.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDrugId implements Serializable {
    @Column(name = "ingredient_id")
    private Long ingredientId;
    @Column(name = "drug_id")
    private Long drugId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientDrugId that)) return false;

        if (!Objects.equals(ingredientId, that.ingredientId)) return false;
        return Objects.equals(drugId, that.drugId);
    }

    @Override
    public int hashCode() {
        int result = ingredientId != null ? ingredientId.hashCode() : 0;
        result = 31 * result + (drugId != null ? drugId.hashCode() : 0);
        return result;
    }
}
