package com.example.backendservice.mapper;

public interface RequestMapping<X, T> {
    X requestToEntity(T request);
}
