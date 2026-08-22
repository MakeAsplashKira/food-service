package com.example.foodservice.UserService;

import com.example.foodservice.UserService.dto.LoginRequest;
import com.example.foodservice.UserService.dto.RegisterRequest;
import com.example.foodservice.UserService.exception.NoSuchUserException;
import com.example.foodservice.UserService.exception.NumberAlreadyTakenException;
import com.example.foodservice.common.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public String register(RegisterRequest request) {
        if(userRepository.existsByNumber(request.phone())) {
            throw new NumberAlreadyTakenException("Number already taken: " + request.phone());
        }


        User user = new User();
        user.setNumber(request.phone());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        userRepository.save(user);


        return jwtService.generateAccessToken(user.getId());

    }

    @Transactional
    public String login(LoginRequest request) {
        User user = userRepository.findByNumber(request.number())
                .orElseThrow(() -> new NoSuchUserException("Number or password was incorrect"));

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new NoSuchUserException("Number or password was incorrect");
        }


        return jwtService.generateAccessToken(user.id);
    }




}
