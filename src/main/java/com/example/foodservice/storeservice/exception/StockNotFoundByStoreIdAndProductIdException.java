package com.example.foodservice.storeservice.exception;

import lombok.Getter;

@Getter
public class StockNotFoundByStoreIdAndProductIdException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Stock not found by store_id %d and product_id %d";
    private final Long storeId;
    private final Long productId;


    public StockNotFoundByStoreIdAndProductIdException(Long storeId, Long productId) {
        super(MESSAGE_TEMPLATE.formatted(storeId, productId));
        this.storeId = storeId;
        this.productId = productId;

    }
}
