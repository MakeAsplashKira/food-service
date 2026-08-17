package com.example.foodservice.RestaurantService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddMenuItemRequest(
        @NotNull
        Long providerMenuItemId,

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Price is required")
        BigDecimal price,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Quantity is required")
        Integer availableQuantity
) {
}
