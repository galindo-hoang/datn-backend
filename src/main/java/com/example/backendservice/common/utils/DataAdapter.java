package com.example.backendservice.common.utils;

public interface DataAdapter<T> {
    T merge(T newData);
}
