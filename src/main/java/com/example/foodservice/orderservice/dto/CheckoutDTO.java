package com.example.foodservice.orderservice.dto;


import com.example.foodservice.orderservice.Order;
import com.example.foodservice.orderservice.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CheckoutDTO {
    private CheckoutDTO() {}

    public record GetCheckoutCommand(Long userId, Long brandId) {
        public static GetCheckoutCommand from(Long userId, Long brandId) {
            return new GetCheckoutCommand(userId, brandId);
        }
    }


    public record CheckoutInfo(
            Long id,
            Long brandId,
            Long storeId,
            List<CheckoutItemInfo> checkoutItems,
            UserInfo userInfo
    ){
        public static CheckoutInfo from(Long storeId, Order order, List<CheckoutItemInfo> checkoutItems, UserInfo userInfo) {
            return new CheckoutInfo(
                    order.getId(),
                    order.getBrandId(),
                    storeId,
                    checkoutItems,
                    userInfo
            );
        }
    }

    public record CheckoutItemInfo(
            Long orderItemId,
            Long productId,

            String name,
            String imageUrl,
            String category,

            boolean isAvailable,
            Integer requestedQuantity,
            BigDecimal unitPrice
    ) {
        public static List<CheckoutItemInfo> from(List<ProductInfo> productInfo, List<StockInfo> stockInfo, List<OrderItem> orderItems) {
            Map<Long, ProductInfo> productMap = productInfo.stream().collect(Collectors.toMap(ProductInfo::id, Function.identity()));
            Map<Long, StockInfo> stockMap = stockInfo.stream().collect(Collectors.toMap(StockInfo::productId, Function.identity()));

            return orderItems.stream()
                    .map(item -> {
                        Long productId = item.getProductId();

                        ProductInfo product = productMap.get(productId);
                        StockInfo stock = stockMap.get(productId);

                        boolean isAvailable = stock != null && item.getRequestedQuantity() <= stock.availableQuantity();
                        BigDecimal unitPrice = (stock != null)? stock.unitPrice(): null;

                        String name = (product != null)? product.name() : null;
                        String imageUrl = (product != null)? product.imageUrl() : null;
                        String category = (product != null)? product.category() : null;

                        return new CheckoutItemInfo(
                                item.getId(),
                                productId,

                                name,
                                imageUrl,
                                category,

                                isAvailable,
                                item.getRequestedQuantity(),
                                unitPrice
                        );
                    }).toList();
        }
    }

    public record ViewCheckoutResponse(
            Long id,
            Long brandId,
            List<CheckoutItemResponse> checkoutItems,
            UserInfo userInfo
    ){
        public static ViewCheckoutResponse from(CheckoutInfo checkoutInfo) {
            return new ViewCheckoutResponse(
                    checkoutInfo.id(),
                    checkoutInfo.brandId(),
                    checkoutInfo.checkoutItems().stream().map(CheckoutItemResponse::from).toList(),
                    checkoutInfo.userInfo
            );
        }
    }

    public  record CheckoutItemResponse(
            Long orderItemId,
            Long productId,

            String name,
            String imageUrl,
            String category,

            boolean available,
            Integer requestedQuantity,
            BigDecimal unitPrice
    ){
        public static CheckoutItemResponse from(CheckoutItemInfo checkoutItemInfo) {
            return new CheckoutItemResponse(
                    checkoutItemInfo.orderItemId(),
                    checkoutItemInfo.productId(),

                    checkoutItemInfo.name(),
                    checkoutItemInfo.imageUrl(),
                    checkoutItemInfo.category(),

                    checkoutItemInfo.isAvailable(),
                    checkoutItemInfo.requestedQuantity(),
                    checkoutItemInfo.unitPrice()
            );
        }
    }
    public  record CheckoutRequest(
            @NotNull @Min(1) Long brandId,
            @Size(max = 100) String commentToStore,
            @Size(max = 100) String commentToCourier){
        public CheckoutCommand toCommand(Long userId) {
            return new CheckoutCommand(
                    userId,
                    this.brandId,
                    this.commentToStore,
                    this.commentToCourier
            );
        }
    }
    public record CheckoutCommand(
            Long userId,
            Long brandId,
            String commentToStore,
            String commentToCourier
    ){

    }


}
