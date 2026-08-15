package com.example.foodservice.UserService;

import com.example.foodservice.UserService.dto.LoginRequest;
import com.example.foodservice.UserService.dto.RegisterRequest;
import com.example.foodservice.UserService.exception.NumberAlreadyTakenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        if(userRepository.existsByNumber(request.number())) {
            throw new NumberAlreadyTakenException("Number already taken: " + request.number());
        }

        User user = new User();
        user.setNumber(request.number());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return "123";

    }

    public String login(LoginRequest request) {
        return "";
    }

}
