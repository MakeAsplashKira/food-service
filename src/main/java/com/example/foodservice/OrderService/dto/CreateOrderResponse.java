package com.example.foodservice.OrderService.dto;

import com.example.foodservice.OrderService.Order;
import com.example.foodservice.OrderService.OrderStatus;
import java.util.List;

public record CreateOrderResponse (
        List<OrderItemResponse> orderItems,
        Long restaurantId,
        Long userId,
        OrderStatus status
){
    public static CreateOrderResponse from (Order order) {
        return new CreateOrderResponse(
                order.getOrderItems().stream().map(OrderItemResponse::from).toList(),
                order.getRestaurantId(),
                order.getUserId(),
                order.getStatus()
        );
    }
}
