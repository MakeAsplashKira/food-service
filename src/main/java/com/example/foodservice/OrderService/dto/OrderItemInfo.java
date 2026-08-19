package com.example.foodservice.OrderService.dto;

import com.example.foodservice.OrderService.OrderItem;

import java.math.BigDecimal;

public record OrderItemInfo(
        Long id,
        Long menuItemId,
        String name,
        BigDecimal unitPrice,
        String category,
        Integer quantity
) {
    static OrderItemInfo from(OrderItem orderItem) {
        return new OrderItemInfo(
                orderItem.getId(),
                orderItem.getMenuItemId(),
                orderItem.getName(),
                orderItem.getUnitPrice(),
                orderItem.getCategory(),
                orderItem.getQuantity()
        );
    }
}
