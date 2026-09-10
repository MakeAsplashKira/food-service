package com.example.foodservice.orderservice.dto;

public record ProductInfo(
        Long id,
        String name,
        String imageUrl,
        String category
) {
}
