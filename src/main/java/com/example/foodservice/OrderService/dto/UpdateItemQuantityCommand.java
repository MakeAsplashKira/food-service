package com.example.foodservice.OrderService.dto;

public record UpdateItemQuantityCommand(
        Long userId,
        Long orderItemId
) {
    public static UpdateItemQuantityCommand from(Long userId, Long orderItemId) {
        return new UpdateItemQuantityCommand(
                userId,
                orderItemId
        );
    }
}
