package com.example.backendservice.common.exception;

import com.example.backendservice.model.payload.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ErrorResponse> handleTechnicalExceptions(TechnicalException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getHttpStatus(), e.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessExceptions(BusinessException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getHttpStatus(), e.getMessage()));
    }
}