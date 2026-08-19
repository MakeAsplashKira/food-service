package com.example.foodservice.OrderService.exception;

public class DuplicateMenuItemException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "Some items id's are duplicated";

    public DuplicateMenuItemException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
