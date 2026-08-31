package com.example.foodservice.storeservice;

import com.example.foodservice.storeservice.dto.ProductDTO.ProductInfo;

import java.util.List;

public interface ProductCatalog {
    List<ProductInfo> getProductsByIds(List<Long> productIds);
}
