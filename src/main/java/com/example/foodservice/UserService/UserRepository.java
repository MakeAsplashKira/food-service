package com.example.foodservice.UserService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNumber(String number);
    Optional<User> findByNumber(String number);
}
