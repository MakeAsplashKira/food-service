package com.example.foodservice.StoreService.repository;
import com.example.foodservice.StoreService.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RestaurantRepository extends JpaRepository<Store, Long> {
    boolean existsByEmail(String email);
    Optional<Store> findByApiKeyAndId(String apiKey, Long id);

}
