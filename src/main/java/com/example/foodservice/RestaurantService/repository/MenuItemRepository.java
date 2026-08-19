package com.example.foodservice.RestaurantService.repository;


import com.example.foodservice.RestaurantService.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByIdAndRestaurantId(Long menuItemId, Long restaurantId);

    @Query("SELECT m FROM MenuItem m JOIN FETCH m.restaurant WHERE m.id in :ids")
    List<MenuItem>findAllByIdWithRestaurant(List<Long> ids);

    @Modifying
    @Query("UPDATE MenuItem m SET m.availableQuantity = m.availableQuantity - :quantity WHERE m.availableQuantity >= :quantity AND m.id = :id")
    int decreaseQuantity(Long id, Integer quantity);
}
