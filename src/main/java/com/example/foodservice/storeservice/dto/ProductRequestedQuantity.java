package com.example.foodservice.storeservice.dto;

public record ProductRequestedQuantity(
        Long menuItemId,
        Integer requestedQuantity
) {
}
