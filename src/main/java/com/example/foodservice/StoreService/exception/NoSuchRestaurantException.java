package com.example.foodservice.StoreService.exception;

public class NoSuchRestaurantException extends RuntimeException {
    public NoSuchRestaurantException(Long restaurantId) {
        super("No such restaurant with id: "+String.valueOf(restaurantId));
    }
}
