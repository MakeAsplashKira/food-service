package com.example.foodservice.brandservice.dto.product;

public record ProductResponse(
        Long id,
        String name,
        String category,
        String imageUrl
) {
    public static ProductResponse from(ProductInfo productInfo) {
        return new ProductResponse(
                productInfo.id(),
                productInfo.name(),
                productInfo.category(),
                productInfo.imageUrl()
        );
    }
}
