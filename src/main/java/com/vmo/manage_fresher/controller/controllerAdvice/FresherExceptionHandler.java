package com.vmo.manage_fresher.controller.controllerAdvice;

import com.vmo.manage_fresher.exception.FresherNotFoundException;
import com.vmo.manage_fresher.model.FresherNotFoundErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FresherExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException (FresherNotFoundException exception) {
        FresherNotFoundErrorResponse error = new FresherNotFoundErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
