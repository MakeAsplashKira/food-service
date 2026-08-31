package com.example.foodservice.storeservice.dto;

public record StoreRegisterResponse(
        String email,
        String address,
        String token
){

}