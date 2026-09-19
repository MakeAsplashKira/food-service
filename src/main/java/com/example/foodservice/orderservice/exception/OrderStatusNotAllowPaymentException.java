package com.example.foodservice.orderservice.exception;

import com.example.foodservice.orderservice.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusNotAllowPaymentException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "You can't pay for order with status %s";
    private final OrderStatus orderStatus;

    public OrderStatusNotAllowPaymentException(OrderStatus orderStatus) {
        super(MESSAGE_TEMPLATE.formatted(orderStatus));
        this.orderStatus = orderStatus;
    }
}
