package com.example.foodservice.storeservice.dto;

import java.math.BigDecimal;

public record AddProductResponse(
   Long id,
   Long restaurantId,
   String name,
   BigDecimal price,
   String category
) {}
