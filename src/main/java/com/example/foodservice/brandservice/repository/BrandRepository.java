package com.example.foodservice.brandservice.repository;

import com.example.foodservice.brandservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);
    Optional<Brand> findByEmail(String email);
    boolean existsByEmail(String  email);
}
