package com.example.foodservice.StoreService.exception;

public class SomeMenuItemsMissingException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "Some menu items was not found";
    public SomeMenuItemsMissingException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
