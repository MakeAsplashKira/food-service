package com.example.foodservice.RestaurantService.repository;


import com.example.foodservice.RestaurantService.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByIdAndRestaurantId(Long menuItemId, Long restaurantId);
}
