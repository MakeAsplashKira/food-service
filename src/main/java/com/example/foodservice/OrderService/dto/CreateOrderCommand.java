package com.example.foodservice.OrderService.dto;

import java.util.List;

public record CreateOrderCommand(
        Long userId,
        List<OrderLine> lines
) {
}
