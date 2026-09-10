package com.example.foodservice.storeservice.exception;

import lombok.Getter;

@Getter
public class StoreExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Store with email %s exists";
    private final String email;

    public StoreExistsException(String email) {
        super(MESSAGE_TEMPLATE.formatted(email));
        this.email = email;
    }
}
