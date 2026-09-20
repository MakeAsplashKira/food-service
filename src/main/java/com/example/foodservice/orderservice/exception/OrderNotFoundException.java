package com.example.foodservice.orderservice.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order %d for user %d not found";
    private final Long userId;
    private final Long brandId;

    public OrderNotFoundException(Long userId, Long brandId) {
        super(MESSAGE_TEMPLATE.formatted(userId, brandId));
        this.userId = userId;
        this.brandId = brandId;
    }

}
