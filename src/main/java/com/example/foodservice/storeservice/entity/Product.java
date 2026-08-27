package com.example.foodservice.storeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<StoreProduct> storeProduct = new ArrayList<>();

    @Column(name = "store_product_id", nullable = false)
    private String externalProductId;

    @Column(nullable = false)
    private String name;

    @Column() //TODO: разработать систему хранения фотографий и их раздачу
    private String imageUrl;

    private String category; //TODO: разработать систему категорий


    public Product(String externalProductId, String name, String category) {
        this.externalProductId = externalProductId;
        this.name = name;
        this.category = category;

    }
}
