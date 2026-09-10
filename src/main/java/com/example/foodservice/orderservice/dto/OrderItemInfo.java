package com.example.foodservice.orderservice.dto;

import com.example.foodservice.orderservice.OrderItem;

import java.math.BigDecimal;

public record OrderItemInfo(
        Long id,
        Long productId,
        Integer requestedQuantity
) {
    static OrderItemInfo from(OrderItem orderItem) {
        return new OrderItemInfo(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getRequestedQuantity()
        );
    }
}
