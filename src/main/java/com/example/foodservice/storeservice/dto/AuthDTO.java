package com.example.foodservice.storeservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthDTO {
    private AuthDTO() {
    }

    public record LoginCommand(String email, String rawPassword) {
    }

    public record LoginRequest(@NotBlank @Email String email,
                               @NotBlank @Size(min = 8, max = 60) String password) {
        public LoginCommand toCommand() {
            return new LoginCommand(this.email, this.password);
        }
    }

    public record AuthResponse(String token) {
    }
}
