package com.example.foodservice.orderservice.payment;

import java.util.UUID;

public record PaymentResponse(String id, String link) {
    public static PaymentResponse from(PaymentInfo paymentInfo) {
        return new PaymentResponse(paymentInfo.id(), paymentInfo.link());
    }
}
