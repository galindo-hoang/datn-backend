package com.example.backendservice.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.Version;

@MappedSuperclass
@Data
public class BaseEntity {
    @Version
    @Column(name = "version")
    private Long version;
}