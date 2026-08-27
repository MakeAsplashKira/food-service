package com.example.foodservice.common.security;

public record StorePrincipal(
        Long storeId,
        Long brandId
) {
    public static StorePrincipal from(Long storeId, Long brandId) {
        return new StorePrincipal(storeId, brandId);
    }
}
