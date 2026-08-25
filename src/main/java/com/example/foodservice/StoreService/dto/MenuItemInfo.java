package com.example.foodservice.StoreService.dto;

import com.example.foodservice.StoreService.entity.Product;

import java.math.BigDecimal;

public record MenuItemInfo(
        Long id,
        Long restaurantId,
        Long providerMenuItemId,
        String name,
        BigDecimal unitPrice,
        String category,
        Integer availableQuantity
) {
    public static MenuItemInfo from(Product product) {
        return new MenuItemInfo(
                product.getId(),
                product.getStore().getId(),
                product.getStoreProductId(),
                product.getName(),
                product.getUnitPrice(),
                product.getCategory(),
                product.getAvailableQuantity()
        );
    }
}
