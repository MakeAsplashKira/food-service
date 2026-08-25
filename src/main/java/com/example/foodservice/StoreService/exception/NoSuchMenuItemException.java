package com.example.foodservice.StoreService.exception;

public class NoSuchMenuItemException extends RuntimeException {
    public NoSuchMenuItemException() {
        super("Some menu item was not found");
    }
}
