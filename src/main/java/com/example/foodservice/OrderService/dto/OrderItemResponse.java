package com.example.foodservice.OrderService.dto;

import com.example.foodservice.OrderService.OrderItem;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        String name,
        BigDecimal unitPrice,
        String category,
        Integer quantity
)
{
    static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getName(),
                orderItem.getUnitPrice(),
                orderItem.getCategory(),
                orderItem.getQuantity()
        );
    }
}
