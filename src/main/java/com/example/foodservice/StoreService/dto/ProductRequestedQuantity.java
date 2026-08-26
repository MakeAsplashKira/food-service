package com.example.foodservice.StoreService.dto;

public record ProductRequestedQuantity(
        Long menuItemId,
        Integer requestedQuantity
) {
}
