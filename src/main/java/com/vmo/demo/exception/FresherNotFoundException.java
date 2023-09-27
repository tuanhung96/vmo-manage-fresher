package com.vmo.demo.exception;

public class FresherNotFoundException extends RuntimeException{
    public FresherNotFoundException(String message) {
        super(message);
    }
}
