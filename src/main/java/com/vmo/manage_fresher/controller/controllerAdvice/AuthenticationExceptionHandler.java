package com.vmo.manage_fresher.controller.controllerAdvice;

import com.vmo.manage_fresher.exception.InvalidUserException;
import com.vmo.manage_fresher.exception.JwtExpiredException;
import com.vmo.manage_fresher.exception.TokenRefreshException;
import com.vmo.manage_fresher.exception.UserDisabledException;
import com.vmo.manage_fresher.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleException(UserDisabledException exception) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(InvalidUserException exception) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(TokenRefreshException exception) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(JwtExpiredException exception) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        System.out.println(exception.getMessage());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
