package com.example.foodservice.orderservice.dto;

public record OrderLine(
        Long menuItemId,
        Integer quantity
) {
}
