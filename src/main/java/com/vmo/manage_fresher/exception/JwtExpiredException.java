package com.vmo.manage_fresher.exception;

public class JwtExpiredException extends RuntimeException{
    public JwtExpiredException(String message) {
        super(message);
    }
}
