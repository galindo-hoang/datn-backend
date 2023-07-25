package com.example.backendservice.model.entity.product;

import com.example.backendservice.common.model.BaseEntity;
import com.example.backendservice.common.utils.DataAdapter;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BaseEntity implements DataAdapter<CategoryEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<DrugEntity> drugs;

    private Timestamp lastModify = new Timestamp(System.currentTimeMillis());


    @Override
    public CategoryEntity merge(CategoryEntity newData) {
        this.setName(newData.getName() != null ? newData.getName() : this.getName());
        return this;
    }
}
