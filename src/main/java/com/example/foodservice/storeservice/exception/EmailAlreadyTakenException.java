package com.example.foodservice.storeservice.exception;


public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String email)
    {
        super("This email already taken: " + email);
    }
}
