package com.example.foodservice.orderservice.exception;

import com.example.foodservice.orderservice.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusIllegalTransitionException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order %d: illegal status transition from %s to %s";
    private final Long orderId;
    private final OrderStatus currentStatus;
    private final OrderStatus newStatus;


    public OrderStatusIllegalTransitionException(Long orderId, OrderStatus currentStatus, OrderStatus newStatus) {
        super(MESSAGE_TEMPLATE.formatted(orderId, currentStatus, newStatus));
        this.orderId = orderId;
        this.currentStatus = currentStatus;
        this.newStatus = newStatus;
    }
}
