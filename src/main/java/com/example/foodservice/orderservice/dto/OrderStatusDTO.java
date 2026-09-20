package com.example.foodservice.orderservice.dto;

import com.example.foodservice.orderservice.OrderStatus;
import jakarta.validation.constraints.NotNull;

public final class OrderStatusDTO {
    private OrderStatusDTO(){}

    public record OrderStatusChangeRequest(
            @NotNull OrderStatus status
    ) {
        public OrderStatusChangeCommand toCommand(Long storeId, Long orderId) {
            return new OrderStatusChangeCommand(
                    storeId,
                    orderId,
                    this.status
            );
        }
    }

    public record OrderStatusChangeCommand(
            Long storeId,
            Long orderId,
            OrderStatus newOrderStatus
    ) {

    }
}
