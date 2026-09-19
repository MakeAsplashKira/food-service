package com.example.foodservice.orderservice.exception;

import com.example.foodservice.orderservice.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusNotAllowCompletePaymentException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order %d with status %s can't complete payment";
    private final OrderStatus orderStatus;
    private final Long id;

    public OrderStatusNotAllowCompletePaymentException(Long id, OrderStatus orderStatus) {
        super(MESSAGE_TEMPLATE.formatted(id, orderStatus));
        this.orderStatus = orderStatus;
        this.id = id;
    }
}
