package com.example.foodservice.storeservice.exception;

import lombok.Getter;

@Getter
public class StoreNotFoundByBrandException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Stores for brand %d not found";
    private final Long brandId;

    public StoreNotFoundByBrandException(Long brandId) {
        super(MESSAGE_TEMPLATE.formatted(brandId));
        this.brandId = brandId;
    }
}
