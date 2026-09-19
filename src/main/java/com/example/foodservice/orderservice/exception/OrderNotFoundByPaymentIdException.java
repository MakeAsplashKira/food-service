package com.example.foodservice.orderservice.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundByPaymentIdException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order not found by payment_id %s";
    private final String paymentId;

    public OrderNotFoundByPaymentIdException(String paymentId) {
        super(MESSAGE_TEMPLATE.formatted(paymentId));
        this.paymentId = paymentId;
    }
}
