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
    CANCELED;

    public boolean canMoveTo(OrderStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus == ACCEPTED;
            case ACCEPTED -> newStatus == PREPARING;
            case PREPARING -> newStatus == READY;
            case READY -> newStatus == DELIVERING;
            case DELIVERING -> newStatus == DELIVERED;
            default -> false;
        };
    }
}
