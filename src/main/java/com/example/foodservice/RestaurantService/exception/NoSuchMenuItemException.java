package com.example.foodservice.RestaurantService.exception;

public class NoSuchMenuItemException extends RuntimeException {
    public NoSuchMenuItemException() {
        super("Some menu item was not found");
    }
}
