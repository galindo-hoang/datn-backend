package com.example.backendservice.mapper;

public interface EntityMapping<X, T> {
    X entityToDto(T entity);
}
