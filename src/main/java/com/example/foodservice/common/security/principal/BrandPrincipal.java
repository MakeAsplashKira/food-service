package com.example.foodservice.common.security.principal;

public record BrandPrincipal(
        Long brandId
) {
    public static BrandPrincipal from(Long brandId) {
        return new BrandPrincipal(brandId);
    }
}
