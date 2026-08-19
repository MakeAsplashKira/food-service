package com.example.foodservice.OrderService.dto;

import com.example.foodservice.OrderService.Order;
import com.example.foodservice.OrderService.OrderStatus;

import java.util.List;

public record CreateOrderInfo(
        List<OrderItemInfo> orderItems,
        Long restaurantId,
        Long userId,
        OrderStatus status
) {
    public static CreateOrderInfo from (Order order) {
        return new CreateOrderInfo(
                order.getOrderItems().stream().map(OrderItemInfo::from).toList(),
                order.getRestaurantId(),
                order.getUserId(),
                order.getStatus()
        );
    }
}
