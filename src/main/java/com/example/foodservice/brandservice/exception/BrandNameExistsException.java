package com.example.foodservice.brandservice.exception;

import lombok.Getter;

@Getter
public class BrandNameExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Brand name %s already exists";
    private final String name;

    public BrandNameExistsException(String name) {
        super(MESSAGE_TEMPLATE.formatted(name));
        this.name = name;
    }
}
