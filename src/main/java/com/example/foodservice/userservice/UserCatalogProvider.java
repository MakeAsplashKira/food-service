package com.example.foodservice.userservice;

import com.example.foodservice.orderservice.UserCatalog;
import com.example.foodservice.orderservice.dto.UserInfo;
import com.example.foodservice.userservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCatalogProvider implements UserCatalog {
    private final UserRepository userRepository;

    @Override
    public UserInfo getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return UserInfo.from(user.getAddress());
    }
}
