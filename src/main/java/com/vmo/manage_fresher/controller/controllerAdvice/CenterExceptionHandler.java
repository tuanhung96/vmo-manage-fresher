package com.vmo.manage_fresher.controller.controllerAdvice;

import com.vmo.manage_fresher.exception.CenterNotFoundException;
import com.vmo.manage_fresher.model.CenterNotFoundErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CenterExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CenterNotFoundErrorResponse> handleException(CenterNotFoundException exception) {
        CenterNotFoundErrorResponse error = new CenterNotFoundErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
