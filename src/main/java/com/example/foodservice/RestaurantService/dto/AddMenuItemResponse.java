package com.example.foodservice.RestaurantService.dto;

import java.math.BigDecimal;

public record AddMenuItemResponse(
   Long id,
   Long restaurant_id,
   String name,
   BigDecimal price,
   String category
) {}
