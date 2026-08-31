package com.example.foodservice.orderservice.dto;

import com.example.foodservice.orderservice.Order;

public record AddItemCommand(
    Long userId,
    Long storeId,
    Long productId
) {
    public static AddItemCommand from(Long userId,
                                      Long storeId,
                                      Long productId) {
        return new AddItemCommand(
                userId,
                storeId,
                productId
        );
    }

    public Order toOrder() {
        return new Order(
                this.userId,
                this.storeId
        );
    }
}
