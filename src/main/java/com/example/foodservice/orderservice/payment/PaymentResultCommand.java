package com.example.foodservice.orderservice.payment;

public record PaymentResultCommand(
        String paymentId,
        String status
) {
}
