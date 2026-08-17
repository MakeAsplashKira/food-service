package com.example.foodservice.RestaurantService.dto;

import java.math.BigDecimal;

public record AddMenuItemResponse(
   Long id,
   Long restaurantId,
   String name,
   BigDecimal price,
   String category
) {}
