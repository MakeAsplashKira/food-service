package com.example.foodservice.storeservice.brand.dto;

import jakarta.validation.constraints.*;

public record RegisterBrandRequest(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 60)
        String password
) {
    public RegisterBrandCommand toCommand() {
        return new RegisterBrandCommand(
                this.name,
                this.email,
                this.password
        );
    }
}
