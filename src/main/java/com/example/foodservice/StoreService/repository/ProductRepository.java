package com.example.foodservice.StoreService.repository;


import com.example.foodservice.StoreService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndStoreId(Long productId, Long storeId);

    @Query("SELECT p FROM Product p JOIN FETCH p.store WHERE p.id in :ids")
    List<Product>findAllByIdWithStore(List<Long> ids);

    @Modifying
    @Query("UPDATE Product p SET p.availableQuantity = p.availableQuantity - :quantity WHERE p.availableQuantity >= :quantity AND p.id = :id")
    int decreaseQuantity(Long id, Integer quantity);

}
