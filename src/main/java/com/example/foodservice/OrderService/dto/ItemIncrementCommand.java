package com.example.foodservice.OrderService.dto;

public record ItemIncrementCommand(
        Long userId,
        Long orderItemId
) {
    public static ItemIncrementCommand from(Long userId, Long orderItemId) {
        return new ItemIncrementCommand(
                userId,
                orderItemId
        );
    }
}
