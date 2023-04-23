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
public class CategoryDrugId implements Serializable {
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "drug_id")
    private Long drugId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDrugId that)) return false;

        if (!Objects.equals(categoryId, that.categoryId)) return false;
        return Objects.equals(drugId, that.drugId);
    }

    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + (drugId != null ? drugId.hashCode() : 0);
        return result;
    }
}
