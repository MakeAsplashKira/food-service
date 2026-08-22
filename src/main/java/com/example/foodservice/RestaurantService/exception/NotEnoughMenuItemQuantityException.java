package com.example.foodservice.RestaurantService.exception;

import lombok.Getter;

@Getter
public class NotEnoughMenuItemQuantityException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Not enough stock for menu item %d: requested %d";
    private final Long menuItemId;
    private final Integer requestedQuantity;

    public NotEnoughMenuItemQuantityException(Long menuItemId, Integer requestedQuantity) {
        super(MESSAGE_TEMPLATE.formatted(menuItemId, requestedQuantity));
        this.menuItemId = menuItemId;
        this.requestedQuantity = requestedQuantity;
    }

}
