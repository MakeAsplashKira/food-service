package com.example.foodservice.orderservice;

import com.example.foodservice.orderservice.dto.StockInfo;

import java.util.List;
import java.util.Map;

public interface StoreCatalog {
    Long getStoreIdByBrandIdAndUserAddress(Long brandId, String address);
    List<StockInfo> findStockByStoreIdAndProductIds(Long storeId, List<Long> productIds);
    void reserveStoreStock(Long storeId, Map<Long, Integer> quantityByProductId);
}
