package com.example.foodservice.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public final class OrderItemQuantityDTO {
    private OrderItemQuantityDTO() {}

    public record UpdateItemQuantityCommand(
            Long userId,
            Long orderItemId
    ) {
        public static UpdateItemQuantityCommand from(Long userId, Long orderItemId) {
            return new UpdateItemQuantityCommand(
                    userId,
                    orderItemId
            );
        }
    }

    public record SetItemQuantityRequest(
            @NotNull @Min(1) Integer requestedQuantity
    ) {
        public SetItemQuantityCommand toCommand(Long userId, Long orderItemId) {
            return new SetItemQuantityCommand(
                    userId,
                    orderItemId,
                    this.requestedQuantity
            );
        }
    }
    public record SetItemQuantityCommand(
            Long userId,
            Long orderItemId,
            Integer requestedQuantity
    ){}
}
