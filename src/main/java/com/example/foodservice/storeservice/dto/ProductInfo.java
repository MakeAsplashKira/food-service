package com.example.foodservice.storeservice.dto;

import com.example.foodservice.storeservice.entity.Product;

import java.math.BigDecimal;

public record ProductInfo(
        Long id,
        Long restaurantId,
        Long providerMenuItemId,
        String name,
        BigDecimal unitPrice,
        String category,
        Integer availableQuantity
) {
    public static ProductInfo from(Product product) {
        return new ProductInfo(1L,1L,1L,"",BigDecimal.ONE,"1", 1); //TODO: заменить заглукшу
    }
}
