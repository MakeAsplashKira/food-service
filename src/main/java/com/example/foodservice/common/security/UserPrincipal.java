package com.example.foodservice.common.security;

public record UserPrincipal(
        Long userId
) {
    public static UserPrincipal from(Long userId) {
        return new UserPrincipal(userId);
    }
}
