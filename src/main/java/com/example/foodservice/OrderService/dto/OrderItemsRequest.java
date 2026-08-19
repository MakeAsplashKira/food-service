package com.example.foodservice.OrderService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemsRequest(
        @NotNull @Positive Long menuItemId,
        @NotNull @Min(1) Integer menuItemQuantity
) {
    public OrderLine toLine() {
        return new OrderLine(menuItemId, menuItemQuantity);
    }
}
