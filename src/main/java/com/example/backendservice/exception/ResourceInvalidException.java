package com.example.backendservice.exception;

import com.example.backendservice.common.exception.BusinessException;

public class ResourceInvalidException extends BusinessException {
    public ResourceInvalidException(String message) {
        super(message);
    }
}