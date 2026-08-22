package com.example.foodservice.OrderService.exception;

import lombok.Getter;

@Getter
public class DuplicateMenuItemException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Menu item with id %d is duplicated in the order";

    private final Long menuItemId;
    public DuplicateMenuItemException(Long menuItemId) {
        super(MESSAGE_TEMPLATE.formatted(menuItemId));
        this.menuItemId = menuItemId;
    }
}
