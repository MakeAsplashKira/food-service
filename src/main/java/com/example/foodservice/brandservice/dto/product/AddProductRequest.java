package com.example.foodservice.brandservice.dto.product;

import jakarta.validation.constraints.NotBlank;

public record AddProductRequest(
        @NotBlank String externalProductId,
        @NotBlank String name,
        @NotBlank String category
) {
    public AddProductCommand toCommand(Long brandId) {
        return new AddProductCommand(
                brandId,
                this.externalProductId,
                this.name,
                this.category
        );
    }
}
