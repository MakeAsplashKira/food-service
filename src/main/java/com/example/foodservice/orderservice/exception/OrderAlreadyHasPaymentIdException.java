package com.example.foodservice.orderservice.exception;

public class OrderAlreadyHasPaymentIdException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Order already waiting for payment";
    public OrderAlreadyHasPaymentIdException() {
        super(DEFAULT_MESSAGE);
    }
}
