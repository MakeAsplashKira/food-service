package com.example.foodservice.storeservice.exception;


public class StoreEmailExistsException extends RuntimeException {
    public StoreEmailExistsException(String email)
    {
        super("This email already taken: " + email);
    }
}
