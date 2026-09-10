package com.example.foodservice.storeservice.repository;


import com.example.foodservice.storeservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    boolean existsByStoreIdAndProductId(Long storeId, Long productId);
    List<Stock> findByStoreId(Long storeId);

    @Modifying
    @Query("DELETE FROM Stock st WHERE st.store.id = :storeId and st.productId = :productId")
    int deleteByStoreIdAndProductId(@Param("storeId") Long storeId, @Param("productId") Long productId);

    @Query("SELECT st FROM Stock st WHERE st.store.id = :storeId and st.productId in :ids")
    List<Stock> findByStoreIdAndProductIds(@Param("storeId") Long storeId, @Param("ids") List<Long> productIds);
}
