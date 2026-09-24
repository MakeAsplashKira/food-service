package com.example.foodservice.storeservice.exception;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoreNotEnoughStock extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Not enough stock in store %d: unavailable products are %s";
    private final Long storeId;
    private final List<Long> productsIds;

    public StoreNotEnoughStock(Long storeId, List<Long> productIds) {
        super(MESSAGE_TEMPLATE.formatted(storeId, productIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")))
        );

        this.storeId = storeId;
        this.productsIds = productIds;
    }
}
