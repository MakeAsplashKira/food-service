package com.example.foodservice.UserService;

import com.example.foodservice.UserService.dto.LoginRequest;
import com.example.foodservice.UserService.dto.RegisterRequest;
import com.example.foodservice.UserService.exception.NoSuchUserException;
import com.example.foodservice.UserService.exception.NumberAlreadyTakenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String register(RegisterRequest request) {
        if(userRepository.existsByNumber(request.phone())) {
            throw new NumberAlreadyTakenException("Number already taken: " + request.phone());
        }

       String apiKey = generateApiKey();

        User user = new User();
        user.setNumber(request.phone());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setApiKey(apiKey);
        userRepository.save(user);

        return apiKey;

    }

    @Transactional
    public String login(LoginRequest request) {
        User user = userRepository.findByNumber(request.number())
                .orElseThrow(() -> new NoSuchUserException("Number or password was incorrect"));

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new NoSuchUserException("Number or password was incorrect");
        }


        return user.getApiKey();
    }


    private String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bit
        random.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

}
