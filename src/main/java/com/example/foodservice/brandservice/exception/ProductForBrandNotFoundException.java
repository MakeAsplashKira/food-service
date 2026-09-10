package com.example.foodservice.brandservice.exception;


import lombok.Getter;

@Getter
public class ProductForBrandNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Product %d for brand %d not found";
    private final Long productId;
    private final Long brandId;


    public ProductForBrandNotFoundException(Long productId, Long brandId) {
        super(MESSAGE_TEMPLATE.formatted(productId, brandId));
        this.productId = productId;
        this.brandId = brandId;
    }
}
