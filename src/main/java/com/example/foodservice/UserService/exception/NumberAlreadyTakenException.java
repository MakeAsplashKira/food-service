package com.example.foodservice.UserService.exception;

public class NumberAlreadyTakenException extends RuntimeException {
    public NumberAlreadyTakenException(String message) {
        super(message);
    }
}
