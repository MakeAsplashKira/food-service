package com.example.foodservice.OrderService.dto;

import com.example.foodservice.OrderService.OrderStatus;

import java.time.Instant;
import java.util.List;

public record CreateOrderResponse (
        Long id,
        List<OrderItemResponse> orderItems,
        Long restaurantId,
        OrderStatus status,
        Instant createdAt
){
    public static CreateOrderResponse from (CreateOrderInfo order) {
        return new CreateOrderResponse(
                order.id(),
                order.orderItems().stream().map(OrderItemResponse::from).toList(),
                order.restaurantId(),
                order.status(),
                order.createdAt()
        );
    }
}
