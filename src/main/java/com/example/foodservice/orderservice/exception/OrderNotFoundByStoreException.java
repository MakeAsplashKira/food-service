package com.example.foodservice.orderservice.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundByStoreException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Order with id %d not found by store with id %d";
    private final Long orderId;
    private final Long storeId;


    public OrderNotFoundByStoreException(Long orderId, Long storeId) {
        super(MESSAGE_TEMPLATE.formatted(orderId, storeId));
        this.orderId = orderId;
        this.storeId = storeId;
    }
}
