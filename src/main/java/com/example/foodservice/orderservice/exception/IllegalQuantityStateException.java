package com.example.foodservice.orderservice.exception;

import lombok.Getter;

@Getter
public class IllegalQuantityStateException extends RuntimeException {
    private final static String MESSAGE_TEMPLATE = "Maximum quantity reached for item %d: Maximum allowed %d";
    private final Long productId;
    private final Integer maxQuantity;


    public IllegalQuantityStateException(Long productId, Integer maxQuantity) {
        super(MESSAGE_TEMPLATE.formatted(productId, maxQuantity));
        this.productId = productId;
        this.maxQuantity = maxQuantity;

    }
}
