package com.example.foodservice.storeservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Stock> stock = new ArrayList<>();

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "created_at")
    @Setter(AccessLevel.NONE)
    private Instant createdAt;

    public Store(Long brandId, String email, String address) {
        this.brandId = brandId;
        this.email = email;
        this.address = address;
    }
}
