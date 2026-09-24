package com.example.foodservice.orderservice.dto;


import com.example.foodservice.orderservice.Order;
import com.example.foodservice.orderservice.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class OrderDTO {
    private OrderDTO() {
    }

    public record AddOrderItemRequest(
            @NotNull @Positive Long brandId,
            @NotNull @Positive Long productId
    ) {
        public AddOrderItemCommand toCommand(Long userId) {
            return new AddOrderItemCommand(userId, brandId, productId);
        }
    }

    public record AddOrderItemCommand(Long userId, Long brandId, Long productId) {
        public Order toOrder() {
            return new Order(
                    this.userId,
                    this.brandId
            );
        }
    }

    public record GetOrderCommand(Long userId, Long brandId) {
    }

    public record OrderItemResponse(
            Long id,
            Long productId,
            Integer requestedQuantity,
            BigDecimal unitPrice
    ) {
        public static OrderItemResponse from(OrderItemInfo orderItemInfo) {
            return new OrderItemResponse(
                    orderItemInfo.id(),
                    orderItemInfo.productId(),
                    orderItemInfo.requestedQuantity(),
                    orderItemInfo.unitPrice()
                    );
        }
    }

    public record UserOrdersResponse(
            Long id,
            Long brandId,
            List<OrderItemResponse> orderItems,
            OrderStatus status,
            Instant pendingAt
    ) {
        public static UserOrdersResponse from(OrderInfo orderInfo) {
            return new UserOrdersResponse(
                    orderInfo.id(),
                    orderInfo.brandId(),
                    orderInfo.orderItems().stream().map(OrderItemResponse::from).toList(),
                    orderInfo.status(),
                    orderInfo.pendingAt()
            );
        }
    }
    public record GetUserOrdersCommand(
            Long userId,
            boolean active
    ) {
        public static GetUserOrdersCommand from(Long userId, boolean active) {
            return new GetUserOrdersCommand(userId, active);
        }
    }
}
