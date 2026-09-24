package com.example.foodservice.storeservice;

import com.example.foodservice.orderservice.StoreCatalog;
import com.example.foodservice.orderservice.dto.StockInfo;
import com.example.foodservice.storeservice.entity.Stock;
import com.example.foodservice.storeservice.entity.Store;
import com.example.foodservice.storeservice.exception.StoreNotEnoughStock;
import com.example.foodservice.storeservice.exception.StoreNotFoundByBrandException;
import com.example.foodservice.storeservice.repository.StockRepository;
import com.example.foodservice.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class StoreCatalogProvider implements StoreCatalog {
    private final StoreRepository storeRepository;
    private final StockRepository stockRepository;

    @Override
    public Long getStoreIdByBrandIdAndUserAddress(Long brandId, String address) {
        Store store = calculateStoreByBrandIdAndCoords(brandId, address);

        return store.getId();
    }

    @Override
    public List<StockInfo> findStockByStoreIdAndProductIds(Long storeId, List<Long> productIds) {
        List<Stock> stocks = stockRepository.findByStoreIdAndProductIds(storeId, productIds);
        return stocks.stream().map(stock -> new StockInfo(
                stock.getProductId(),
                storeId,
                stock.getProductId(),

                stock.getAvailableQuantity(),
                stock.getUnitPrice()
        )).toList();
    }

    @Override
    @Transactional
    public void reserveStoreStock(Long storeId, Map<Long, Integer> quantityByProductId) {
        Map<Long, Integer> sortedMap = new TreeMap<>(quantityByProductId);
        List<Long> unreservedProductIds = new ArrayList<>();

        for (Long productId : sortedMap.keySet()) {
            int rowUpdated = stockRepository.reserveStock(storeId, productId, sortedMap.get(productId));

            if(rowUpdated == 0) {
                unreservedProductIds.add(productId);
            }
        }

        if( ! unreservedProductIds.isEmpty()) {
            throw new StoreNotEnoughStock(storeId, unreservedProductIds);
        }
    }

    private Store calculateStoreByBrandIdAndCoords(Long brandId, String address) {
        List<Store> stores = storeRepository.findByBrandId(brandId);
        if (stores.isEmpty()) {
            throw new StoreNotFoundByBrandException(brandId);
        }
        // в будущем будем получать один Store по координатам, пока отдаем первый.
        return stores.getFirst();
    }
}
