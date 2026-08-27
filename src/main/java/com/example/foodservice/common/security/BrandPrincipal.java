package com.example.foodservice.common.security;

public record BrandPrincipal(
        Long brandId
) {
    public static BrandPrincipal from(Long brandId) {
        return new BrandPrincipal(brandId);
    }
}
