package com.example.foodservice.UserService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
    @NotBlank(message = "Number length should be in range 8 - 15 digits")
    @Size(min = 8, max = 15)
    String number,

    @NotBlank(message = "Password length should be in range 8 - 60 characters")
    @Size(min = 8, max = 60)
    String password
) {}
