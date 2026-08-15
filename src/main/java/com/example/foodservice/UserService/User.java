package com.example.foodservice.UserService;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
