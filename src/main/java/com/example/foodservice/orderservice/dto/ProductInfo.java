package com.example.foodservice.orderservice.dto;

public record ProductInfo(
        Long id,
        String externalProductId,
        String name,
        String imageUrl,
        String category
) {
}
