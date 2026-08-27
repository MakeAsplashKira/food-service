package com.example.foodservice.storeservice.brand.dto;


import com.example.foodservice.storeservice.brand.Brand;

public record BrandInfo(
        long id,
        String name,
        String email
)

{
 public static BrandInfo from(Brand brand) {
     return new BrandInfo(
             brand.getId(),
             brand.getName(),
             brand.getEmail()
     );
 }
}
