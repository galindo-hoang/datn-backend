package com.example.backendservice.model.entity.product;

import com.example.backendservice.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TopSearchDrug")
@Builder
public class TopSearchDrugEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long count;
    private Timestamp lastSearch;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "drug_Id")
    private DrugEntity drug;
}
