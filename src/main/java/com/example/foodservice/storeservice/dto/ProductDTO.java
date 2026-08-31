package com.example.foodservice.storeservice.dto;


import com.example.foodservice.storeservice.entity.Stock;

import java.math.BigDecimal;

public final class ProductDTO {
    private ProductDTO() {
    }

    public record ProductInfo(
            Long id,
            String name,
            String category,
            String imageUrl
    ) {

    }

    public record ProductOfferInfo(
            Long id,
            Long productId,
            Long storeId,
            String name,
            String category,
            Integer availableQuantity,
            BigDecimal unitPrice,
            String imageUrl
    ) {
        public static ProductOfferInfo from(ProductInfo productInfo, Stock stock) {
            return new ProductOfferInfo(
                    stock.getId(),
                    stock.getProductId(),
                    stock.getStore().getId(),
                    productInfo.name(),
                    productInfo.category(),
                    stock.getAvailableQuantity(),
                    stock.getUnitPrice(),
                    productInfo.imageUrl()
                    );
        }
    }

    public record ProductOfferResponse(
            Long id,
            Long productId,
            Long storeId,
            String name,
            String category,
            Integer availableQuantity,
            BigDecimal unitPrice,
            String imageUrl
    ) {
        public static ProductOfferResponse from(ProductOfferInfo info) {
            return new ProductOfferResponse(
                    info.id(),
                    info.productId(),
                    info.storeId(),
                    info.name(),
                    info.category(),
                    info.availableQuantity(),
                    info.unitPrice(),
                    info.imageUrl()
            );
        }
    }
}
