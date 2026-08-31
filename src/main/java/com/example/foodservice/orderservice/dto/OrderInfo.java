package com.example.foodservice.orderservice.dto;

import com.example.foodservice.orderservice.Order;
import com.example.foodservice.orderservice.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderInfo(
        Long id,
        List<OrderItemInfo> orderItems,
        Long restaurantId,
        Long userId,
        OrderStatus status,
        Instant createdAt
) {
    public static OrderInfo from(Order order) {
        return new OrderInfo(
                order.getId(),
                order.getOrderItems().stream().map(OrderItemInfo::from).toList(),
                order.getStoreId(),
                order.getUserId(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
