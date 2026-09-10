package com.example.foodservice.userservice.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "User with %d not found";
    private final Long userId;

    public UserNotFoundException(Long userId) {
        super(MESSAGE_TEMPLATE.formatted(userId));
        this.userId = userId;
    }
}
