package com.vmo.manage_fresher.controller.controllerAdvice;

import com.vmo.manage_fresher.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleException (ConstraintViolationException exception) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getConstraintViolations().stream().toList().get(0).getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
