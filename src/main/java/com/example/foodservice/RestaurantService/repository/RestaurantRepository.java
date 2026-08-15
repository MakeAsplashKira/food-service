package com.example.foodservice.RestaurantService.repository;
import com.example.foodservice.RestaurantService.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByEmail(String email);
    Optional<Restaurant> findByApiKeyAndId(String apiKey, Long id);

}
