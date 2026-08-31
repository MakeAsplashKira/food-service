package com.example.foodservice.orderservice.exception;

import lombok.Getter;

@Getter
public class OrderItemNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order item %d not found";
    private final Long orderItemId;

    public OrderItemNotFoundException(Long orderItemId) {
        super(MESSAGE_TEMPLATE.formatted(orderItemId));
        this.orderItemId = orderItemId;
    }
}
