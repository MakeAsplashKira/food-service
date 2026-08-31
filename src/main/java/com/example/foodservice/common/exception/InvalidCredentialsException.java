package com.example.foodservice.common.exception;

public class InvalidCredentialsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Неверный логин или пароль";

    public InvalidCredentialsException(
    ) {
        super(DEFAULT_MESSAGE);
    }
}
