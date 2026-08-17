package com.example.foodservice.common.dto;

import com.example.foodservice.RestaurantService.entity.MenuItem;

import java.math.BigDecimal;

public record MenuItemResponse(
        Long restaurantId,
        Long providerMenuItemId,
        String name,
        BigDecimal unitPrice,
        String category,
        Integer quantity
) {
    public static MenuItemResponse from(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getRestaurant().getId(),
                menuItem.getProviderMenuItemId(),
                menuItem.getName(),
                menuItem.getUnitPrice(),
                menuItem.getCategory(),
                menuItem.getAvailableQuantity()
        );
    }
}
