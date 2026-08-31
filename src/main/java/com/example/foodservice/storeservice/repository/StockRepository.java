package com.example.foodservice.storeservice.repository;


import com.example.foodservice.storeservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    boolean existsByStoreIdAndProductId(Long storeId, Long productId);
    List<Stock> findByStoreId(Long storeId);
}
