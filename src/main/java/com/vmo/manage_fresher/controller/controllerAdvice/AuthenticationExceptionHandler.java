package com.vmo.manage_fresher.controller.controllerAdvice;

import com.vmo.manage_fresher.exception.InvalidUserException;
import com.vmo.manage_fresher.exception.UserDisabledException;
import com.vmo.manage_fresher.model.InvalidUserErrorResponse;
import com.vmo.manage_fresher.model.UserDisabledErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleException(UserDisabledException exception) {
        UserDisabledErrorResponse error = new UserDisabledErrorResponse();
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(InvalidUserException exception) {
        InvalidUserErrorResponse error = new InvalidUserErrorResponse();
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
