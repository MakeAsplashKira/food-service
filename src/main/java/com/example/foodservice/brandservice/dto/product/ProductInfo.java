package com.example.foodservice.brandservice.dto.product;

import com.example.foodservice.brandservice.entity.Product;

public record ProductInfo(
        Long id,
        String name,
        String category,
        String imageUrl
) {
    public static ProductInfo from(Product product) {
        return new ProductInfo(
          product.getId(),
          product.getName(),
          product.getCategory(),
          product.getImageUrl()
        );
    }
}
