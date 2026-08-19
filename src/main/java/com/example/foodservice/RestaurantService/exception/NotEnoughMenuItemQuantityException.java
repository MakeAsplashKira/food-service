package com.example.foodservice.RestaurantService.exception;

public class NotEnoughMenuItemQuantityException extends RuntimeException {
    public NotEnoughMenuItemQuantityException() {
        super("Not enough item quantity in stock");
    }
}
