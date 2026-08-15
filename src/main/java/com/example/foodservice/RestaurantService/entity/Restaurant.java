package com.example.foodservice.RestaurantService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "restaurants")
public class Restaurant {

    public Restaurant(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @Column(name = "api_key", nullable = false, unique = true, length = 64)
    private String apiKey;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
