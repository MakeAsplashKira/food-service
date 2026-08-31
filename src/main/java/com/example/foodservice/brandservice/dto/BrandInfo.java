package com.example.foodservice.brandservice.dto;


import com.example.foodservice.brandservice.entity.Brand;

public record BrandInfo(
        long id,
        String name,
        String email,
        String imageUrl
)

{
 public static BrandInfo from(Brand brand) {
     return new BrandInfo(
             brand.getId(),
             brand.getName(),
             brand.getEmail(),
             brand.getImageUrl()
     );
 }
}
