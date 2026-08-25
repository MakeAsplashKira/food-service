package com.example.foodservice.StoreService.dto;

public record MenuItemRequestedQuantity(
        Long menuItemId,
        Integer requestedQuantity
) {
}
