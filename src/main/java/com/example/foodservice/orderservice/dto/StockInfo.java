package com.example.foodservice.orderservice.dto;

import java.math.BigDecimal;

public record StockInfo(
        Long id,
        Long storeId,
        Long productId,
        Integer availableQuantity,
        BigDecimal unitPrice
) {

}
