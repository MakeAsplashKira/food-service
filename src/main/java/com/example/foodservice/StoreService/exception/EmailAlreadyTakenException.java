package com.example.foodservice.StoreService.exception;


public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String email)
    {
        super("This email already taken: " + email);
    }
}
