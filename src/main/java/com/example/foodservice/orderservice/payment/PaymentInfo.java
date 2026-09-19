package com.example.foodservice.orderservice.payment;

import java.util.UUID;

public record PaymentInfo(String id, String link) {
    public static PaymentInfo from(String id, String link) {
        return new PaymentInfo(id, link);
    }
}
