package com.example.foodservice.storeservice.dto;

import com.example.foodservice.storeservice.entity.Store;
import com.example.foodservice.storeservice.entity.Stock;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public final class StockDTO {
    private StockDTO() {
    }

    public record AddStockCommand(
            Long storeId,
            Long productId,
            Integer availableQuantity,
            BigDecimal unitPrice
    ) {
        public Stock toStock(Store store) {
            return new Stock(
                    store,
                    productId,
                    availableQuantity,
                    unitPrice
            );
        }
    }

    public record AddStockRequest(
            @NotNull @Positive Integer availableQuantity,
            @NotNull @Positive BigDecimal unitPrice
    ) {
        public AddStockCommand toCommand(Long storeId, Long productId) {
            return new AddStockCommand(
                    storeId,
                    productId,
                    this.availableQuantity,
                    this.unitPrice
            );
        }
    }

    public record AddStockResponse(
            Long id,
            Long storeId,
            Long productId,
            Integer availableQuantity,
            BigDecimal unitPrice
    ) {
        public static AddStockResponse from(StockInfo productInfo) {
            return new AddStockResponse(
                    productInfo.id(),
                    productInfo.storeId(),
                    productInfo.productId(),
                    productInfo.availableQuantity,
                    productInfo.unitPrice
            );
        }

    }

    public record StockInfo(Long id, Long storeId, Long productId, BigDecimal unitPrice, Integer availableQuantity) {
        public static StockInfo from(Stock stock) {
            return new StockInfo(
                    stock.getId(),
                    stock.getStore().getId(),
                    stock.getProductId(),
                    stock.getUnitPrice(),
                    stock.getAvailableQuantity()
            );
        }
    }

    public record DeleteStockByProductIdCommand(Long storeId,Long productId) {
        public static DeleteStockByProductIdCommand from(Long storeId, Long productId) {
            return new DeleteStockByProductIdCommand(
              storeId,
              productId
            );
        }
    }


}
