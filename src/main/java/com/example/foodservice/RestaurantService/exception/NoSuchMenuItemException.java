package com.example.foodservice.RestaurantService.exception;

public class NoSuchMenuItemException extends RuntimeException {
    public NoSuchMenuItemException(Long menuItemId) {
        super("No such menu item with id: " + String.valueOf(menuItemId));
    }
}
