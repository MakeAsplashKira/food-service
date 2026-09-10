package com.example.foodservice.orderservice.exception;

import com.example.foodservice.orderservice.OrderStatus;
import lombok.Getter;

@Getter
public class OrderUnmodifiableException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order can't be modified, because its current status is %s";
    private final String status;

    public OrderUnmodifiableException(OrderStatus status) {
        super(MESSAGE_TEMPLATE.formatted(status));
        this.status = status.toString();
    }
}
