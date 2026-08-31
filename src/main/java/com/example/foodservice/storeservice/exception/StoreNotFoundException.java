package com.example.foodservice.storeservice.exception;


import lombok.Getter;

@Getter
public class StoreNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Store with id %d not found";

    private final Long storeId;

    public StoreNotFoundException(Long storeId) {
        super(MESSAGE_TEMPLATE.formatted(storeId));
        this.storeId = storeId;
    }
}
