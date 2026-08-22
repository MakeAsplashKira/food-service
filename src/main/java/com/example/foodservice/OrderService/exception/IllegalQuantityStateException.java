package com.example.foodservice.OrderService.exception;

import lombok.Getter;

@Getter
public class IllegalQuantityStateException extends RuntimeException {
    private final static String MESSAGE_TEMPLATE = "Maximum quantity reached for item %d: Maximum allowed %d";
    private final Long menuItemId;
    private final Integer maxQuantity;


    public IllegalQuantityStateException(Long menuItemId, Integer maxQuantity) {
        super(MESSAGE_TEMPLATE.formatted(menuItemId, maxQuantity));
        this.menuItemId = menuItemId;
        this.maxQuantity = maxQuantity;

    }
}
