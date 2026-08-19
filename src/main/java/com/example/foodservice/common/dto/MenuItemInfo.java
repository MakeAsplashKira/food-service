package com.example.foodservice.common.dto;

import com.example.foodservice.RestaurantService.entity.MenuItem;

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
    public static MenuItemInfo from(MenuItem menuItem) {
        return new MenuItemInfo(
                menuItem.getId(),
                menuItem.getRestaurant().getId(),
                menuItem.getProviderMenuItemId(),
                menuItem.getName(),
                menuItem.getUnitPrice(),
                menuItem.getCategory(),
                menuItem.getAvailableQuantity()
        );
    }
}
