package com.example.foodservice.orderservice.dto;

import java.util.List;

public record CreateOrderCommand(
        Long userId,
        List<OrderLine> lines
) {
}
