package com.example.foodservice.orderservice.dto;

import com.example.foodservice.orderservice.Order;
import com.example.foodservice.orderservice.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderInfo(
        Long id,
        Long brandId,
        List<OrderItemInfo> orderItems,
        OrderStatus status,
        Instant pendingAt
) {
    public static OrderInfo from(Order order) {
        return new OrderInfo(
                order.getId(),
                order.getBrandId(),
                order.getOrderItems().stream().map(OrderItemInfo::from).toList(),
                order.getStatus(),
                order.getPendingAt()
        );
    }
}
