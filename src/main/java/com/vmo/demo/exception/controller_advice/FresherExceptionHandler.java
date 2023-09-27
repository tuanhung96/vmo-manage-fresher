package com.vmo.demo.exception.controller_advice;

import com.vmo.demo.exception.FresherNotFoundException;
import com.vmo.demo.model.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class FresherExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException (FresherNotFoundException exception) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
