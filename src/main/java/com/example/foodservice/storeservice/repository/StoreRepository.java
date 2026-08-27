package com.example.foodservice.storeservice.repository;
import com.example.foodservice.storeservice.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByEmail(String email);
    Optional<Store> findByApiKeyAndId(String apiKey, Long id);

}
