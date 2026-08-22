package com.example.foodservice.OrderService.dto;

public record AddItemCommand(
    Long userId,
    Long restaurantId,
    Long menuItemId
) {
    public static AddItemCommand from(Long userId,
                                      Long restaurantId,
                                      Long menuItemId) {
        return new AddItemCommand(
                userId,
                restaurantId,
                menuItemId
        );
    }
}
