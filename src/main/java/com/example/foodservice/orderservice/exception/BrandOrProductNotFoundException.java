package com.example.foodservice.orderservice.exception;

import lombok.Getter;

@Getter
public class BrandOrProductNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Brand %d or product %d not found";
    private final Long brandId;
    private final Long productId;

    public BrandOrProductNotFoundException(Long brandId, Long productId) {
        super(MESSAGE_TEMPLATE.formatted(brandId, productId));
        this.brandId = brandId;
        this.productId = productId;
    }
}
