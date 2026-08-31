package com.example.foodservice.brandservice.dto.product;

import com.example.foodservice.brandservice.entity.Brand;
import com.example.foodservice.brandservice.entity.Product;

public record AddProductCommand(
        Long brandId,
        String externalProductId,
        String name,
        String category
) {
    public Product toProductWithBrand(Brand brand) {
        return new Product(
          brand,
          this.externalProductId,
          this.name,
          this.category
        );

    }
}
