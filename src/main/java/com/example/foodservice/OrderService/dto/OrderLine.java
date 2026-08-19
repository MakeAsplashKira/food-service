package com.example.foodservice.OrderService.dto;

public record OrderLine(
        Long menuItemId,
        Integer quantity
) {
}
