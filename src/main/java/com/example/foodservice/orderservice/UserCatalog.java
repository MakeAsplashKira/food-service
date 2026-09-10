package com.example.foodservice.orderservice;

import com.example.foodservice.orderservice.dto.UserInfo;

public interface UserCatalog {
    UserInfo getUserInfo(Long userId);
}
