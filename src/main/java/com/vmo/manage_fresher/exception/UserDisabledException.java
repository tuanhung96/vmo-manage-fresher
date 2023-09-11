package com.vmo.manage_fresher.exception;

public class UserDisabledException extends RuntimeException{
    public UserDisabledException(String message) {
        super(message);
    }
}
