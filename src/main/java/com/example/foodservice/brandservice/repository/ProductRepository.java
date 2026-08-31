package com.example.foodservice.brandservice.repository;


import com.example.foodservice.brandservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Stock st SET st.availableQuantity = st.availableQuantity - :quantity WHERE st.availableQuantity >= :quantity AND st.productId = :id")
    int decreaseQuantity(Long id, Integer quantity);

    List<Product> findByBrandId(Long brandId);
}

