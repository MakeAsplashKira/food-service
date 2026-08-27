package com.example.foodservice.storeservice.exception;

public class NoSuchMenuItemException extends RuntimeException {
    public NoSuchMenuItemException() {
        super("Some menu item was not found");
    }
}
