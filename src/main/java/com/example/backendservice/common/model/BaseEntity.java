package com.example.backendservice.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {
    @Version
    @Column(name = "version")
    private Long version;
}