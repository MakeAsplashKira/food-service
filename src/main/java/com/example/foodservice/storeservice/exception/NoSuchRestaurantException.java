package com.example.foodservice.storeservice.exception;

public class NoSuchRestaurantException extends RuntimeException {
    public NoSuchRestaurantException(Long restaurantId) {
        super("No such restaurant with id: "+String.valueOf(restaurantId));
    }
}
