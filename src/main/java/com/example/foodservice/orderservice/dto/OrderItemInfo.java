package com.example.foodservice.orderservice.dto;

import com.example.foodservice.orderservice.OrderItem;
import com.example.foodservice.orderservice.dto.OrderDTO.OrderItemResponse;

import java.math.BigDecimal;

public record OrderItemInfo(
        Long id,
        Long productId,
        Integer requestedQuantity,
        BigDecimal unitPrice
) {
    public static OrderItemInfo from(OrderItem orderItem) {
        return new OrderItemInfo(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getRequestedQuantity(),
                orderItem.getUnitPrice()
        );
    }

}
