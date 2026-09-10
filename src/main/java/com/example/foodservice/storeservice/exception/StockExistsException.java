package com.example.foodservice.storeservice.exception;


import lombok.Getter;

@Getter
public class StockExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Stock already exists for store %d and product %d";
    private final Long storeId;
    private final Long productId;

    public StockExistsException(Long storeId, Long productId)
    {
        super(MESSAGE_TEMPLATE.formatted(storeId, productId));
        this.storeId = storeId;
        this.productId = productId;
    }
}
