package com.example.foodservice.storeservice.repository;


import com.example.foodservice.storeservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.storeProduct sp " +
            "JOIN FETCH sp.store s " +
            "WHERE p.id = :productId AND s.id = :storeId")
    Optional<Product> findByIdAndStoreId(Long productId, Long storeId);

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.storeProduct sp " +
            "JOIN FETCH sp.store " +
            "WHERE p.id in :ids")
    List<Product>findAllByIdWithStore(List<Long> ids);

    @Modifying
    @Query("UPDATE StoreProduct sp SET sp.availableQuantity = sp.availableQuantity - :quantity WHERE sp.availableQuantity >= :quantity AND sp.product.id = :id")
    int decreaseQuantity(Long id, Integer quantity);

}

