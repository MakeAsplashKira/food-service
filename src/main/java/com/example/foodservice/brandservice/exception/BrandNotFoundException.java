package com.example.foodservice.brandservice.exception;

import lombok.Getter;

@Getter
public class BrandNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Brand with id %d not found";
    private final Long brandId;

    public BrandNotFoundException(Long brandId)
    {
        super(MESSAGE_TEMPLATE.formatted(brandId));
        this.brandId = brandId;
    }
}
