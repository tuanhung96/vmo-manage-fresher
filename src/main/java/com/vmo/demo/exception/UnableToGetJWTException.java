package com.vmo.demo.exception;

public class UnableToGetJWTException extends RuntimeException {
    public UnableToGetJWTException(String message) {
        super(message);
    }
}
