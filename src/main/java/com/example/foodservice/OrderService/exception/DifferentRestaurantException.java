package com.example.foodservice.OrderService.exception;

public class DifferentRestaurantException extends RuntimeException {
    public DifferentRestaurantException() {
        super("You can order only from one restaurant");
    }
}
