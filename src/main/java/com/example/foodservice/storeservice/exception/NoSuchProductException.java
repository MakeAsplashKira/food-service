package com.example.foodservice.storeservice.exception;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException() {
        super("Some menu item was not found");
    }
}
