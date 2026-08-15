package com.example.foodservice.common.dto;


import com.example.foodservice.common.RequestMetrics;

public record ApiResponse<T>(
        boolean success,
        T data,
        String error,
        RequestMetrics.Stats stats
        ) {}
