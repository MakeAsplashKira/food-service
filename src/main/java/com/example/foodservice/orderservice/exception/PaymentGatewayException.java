package com.example.foodservice.orderservice.exception;

public class PaymentGatewayException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Payment gateway exception";

    public PaymentGatewayException() {
        super(DEFAULT_MESSAGE);
    }
}
