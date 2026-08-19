package com.example.foodservice.OrderService.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty
        List<@Valid OrderItemsRequest> items)
{
        public CreateOrderCommand toCommand(Long userId) {
                return new CreateOrderCommand(
                        userId,
                        items.stream().map(OrderItemsRequest::toLine).toList()
                );
        }
}



