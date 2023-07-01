package com.example.backendservice.exception;

import com.example.backendservice.common.exception.TechnicalException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends TechnicalException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

}
