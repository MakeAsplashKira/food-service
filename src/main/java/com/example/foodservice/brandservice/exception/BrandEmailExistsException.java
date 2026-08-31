package com.example.foodservice.brandservice.exception;

import lombok.Getter;

@Getter
public class BrandEmailExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Brand email %s already exists";
    private final String email;

    public BrandEmailExistsException(String email) {
        super(MESSAGE_TEMPLATE.formatted(email));
        this.email = email;
    }
}
