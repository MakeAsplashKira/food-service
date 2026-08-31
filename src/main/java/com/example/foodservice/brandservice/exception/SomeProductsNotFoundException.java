package com.example.foodservice.brandservice.exception;

public class SomeProductsNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Some products not found";
    public SomeProductsNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
