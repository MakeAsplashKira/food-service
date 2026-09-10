package com.example.foodservice.orderservice.exception;


import lombok.Getter;

@Getter
public class OrderItemUnavailableException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order item %d unavailable: not enough stock";
    private final Long orderItemId;

    public OrderItemUnavailableException(Long orderItemId) {
        super(MESSAGE_TEMPLATE.formatted(orderItemId));
        this.orderItemId = orderItemId;
    }

}
