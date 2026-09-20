package com.example.foodservice.orderservice.storenotifier;

public record OrderPaid(Long storeId, Long orderId) {

    public static OrderPaid from(Long storeId, Long orderId) {
        return new OrderPaid(storeId, orderId);
    }
}
