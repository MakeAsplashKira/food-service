package com.example.foodservice.orderservice;

import com.example.foodservice.orderservice.dto.StockInfo;

import java.util.List;

public interface StoreCatalog {
    Long getStoreIdByBrandIdAndUserAdress(Long brandId, String address);
    List<StockInfo> findStockByStoreIdAndProductIds(Long storeId, List<Long> productIds);
}
