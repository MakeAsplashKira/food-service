package com.example.foodservice.storeservice.brand.dto;

public record RegisterBrandResponse (
        long id,
        String name,
        String email
){
    public static RegisterBrandResponse from(BrandInfo brandInfo) {
        return new RegisterBrandResponse(
                brandInfo.id(),
                brandInfo.name(),
                brandInfo.email()
        );
    }
}
