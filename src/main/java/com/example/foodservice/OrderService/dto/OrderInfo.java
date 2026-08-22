package com.example.foodservice.OrderService.dto;

import com.example.foodservice.OrderService.Order;
import com.example.foodservice.OrderService.OrderStatus;

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
                order.getRestaurantId(),
                order.getUserId(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
