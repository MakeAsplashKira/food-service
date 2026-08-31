package com.example.foodservice.orderservice.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long menuItemId,
        String name,
        BigDecimal unitPrice,
        String category,
        Integer quantity
)
{
    static OrderItemResponse from(OrderItemInfo orderItem) {
        return new OrderItemResponse(
                orderItem.id(),
                orderItem.menuItemId(),
                orderItem.name(),
                orderItem.unitPrice(),
                orderItem.category(),
                orderItem.quantity()
        );
    }
}
