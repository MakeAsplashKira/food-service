package com.example.foodservice.brandservice.dto;

import com.example.foodservice.brandservice.entity.Brand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthDTO {
    private AuthDTO() {}

    //register
    public record RegisterCommand(String name, String email, String rawPassword) {
        public Brand toBrand() {
            return new Brand(this.name, this.email);
        }
    }

    public record RegisterRequest(@NotBlank String name, @NotBlank @Email String email,
                                  @NotBlank @Size(min = 8, max = 60) String password) {
        public RegisterCommand toCommand() {
            return new RegisterCommand(this.name, this.email, this.password);
        }
    }

    // login
    public record LoginCommand(String email, String password) {
    }

    public record LoginRequest(@NotBlank @Email String email,
                                      @NotBlank @Size(min = 8, max = 60) String password) {
        public LoginCommand toCommand() {
            return new LoginCommand(this.email, this.password);
        }
    }

    //response
    public record AuthResponse(String token) {
        public static AuthResponse from(String token) {
            return new AuthResponse(token);
        }
    }
}
