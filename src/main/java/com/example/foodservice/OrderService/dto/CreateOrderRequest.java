package com.example.foodservice.OrderService.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

public record CreateOrderRequest(
        @NotEmpty
        Map<String, Integer> items
) {}
