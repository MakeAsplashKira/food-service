package com.example.foodservice.UserService.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String number,

        @NotBlank
        String password
) {}
