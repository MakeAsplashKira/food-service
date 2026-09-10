package com.example.foodservice.orderservice;


public enum OrderStatus {
    DRAFT,
    AWAITING_PAYMENT,
    PENDING,
    ACCEPTED,
    PREPARING,
    READY,
    DELIVERING,
    DELIVERED,
    CANCELED
}
