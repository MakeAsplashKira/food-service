package com.example.foodservice.orderservice.dto;

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
