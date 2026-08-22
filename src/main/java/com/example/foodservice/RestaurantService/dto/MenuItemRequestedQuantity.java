package com.example.foodservice.RestaurantService.dto;

public record MenuItemRequestedQuantity(
        Long menuItemId,
        Integer requestedQuantity
) {
}
