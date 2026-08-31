package com.example.foodservice.brandservice.exception;

import lombok.Getter;

@Getter
public class BrandAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Brand with name %s or with email %s already exists";
    private final String name;
    private final String email;


    public BrandAlreadyExistsException(String name, String email)
    {
        super(MESSAGE_TEMPLATE.formatted(name, email));
        this.name = name;
        this.email = email;
    }
}
