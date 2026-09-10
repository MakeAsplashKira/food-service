package com.example.foodservice.orderservice;


import com.example.foodservice.orderservice.dto.ProductInfo;

import java.util.List;

public interface ProductCatalogByOrder {
    boolean existsByBrandIdAndProductId(Long brandId, Long productId);
    List<ProductInfo> getProductsByIdsForOrder(List<Long> productIds);
}
