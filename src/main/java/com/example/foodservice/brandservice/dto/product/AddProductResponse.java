package com.example.foodservice.brandservice.dto.product;

public record AddProductResponse(
        Long id,
        String name,
        String category,
        String imageUrl
) {
    public static AddProductResponse from(ProductInfo productInfo) {
        return new AddProductResponse(
                productInfo.id(),
                productInfo.name(),
                productInfo.category(),
                productInfo.imageUrl()
        );
    }
}
