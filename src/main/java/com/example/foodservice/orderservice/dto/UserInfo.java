package com.example.foodservice.orderservice.dto;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;

public record UserInfo (
        String address
){
    public static UserInfo from(String address) {
        return new UserInfo(address);
    }
}
