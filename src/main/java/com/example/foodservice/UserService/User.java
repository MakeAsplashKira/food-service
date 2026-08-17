package com.example.foodservice.UserService;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false, length = 15)
    String number;

    @Column(nullable = false, length = 60)
    String passwordHash;

    @Column
    String name;

    @Column
    String address;

    @Column(name = "api_key", nullable = false, unique = true)
    String apiKey;

    @CreationTimestamp
    @Column(name = "created_at")
    Instant createdAt;

}
