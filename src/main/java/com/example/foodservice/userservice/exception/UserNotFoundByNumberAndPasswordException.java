package com.example.foodservice.userservice.exception;

public class UserNotFoundByNumberAndPasswordException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "User not found: number or password is incorrect";

    public UserNotFoundByNumberAndPasswordException() {
        super(DEFAULT_MESSAGE);
    }
}
