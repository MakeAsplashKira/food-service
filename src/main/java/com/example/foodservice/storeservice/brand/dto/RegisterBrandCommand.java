package com.example.foodservice.storeservice.brand.dto;

import com.example.foodservice.storeservice.brand.Brand;

public record RegisterBrandCommand (
        String name,
        String email,
        String rawPassword
){
    public Brand toBrand() {
        return new Brand(
                this.name,
                this.email
        );
    }
}
