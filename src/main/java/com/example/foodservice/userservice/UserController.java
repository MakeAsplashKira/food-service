package com.example.foodservice.userservice;


import com.example.foodservice.userservice.dto.LoginRequest;
import com.example.foodservice.userservice.dto.LoginResponse;
import com.example.foodservice.userservice.dto.RegisterRequest;
import com.example.foodservice.userservice.dto.RegisterResponse;
import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {

        String token = userService.register(request);

        return responseBuilder.created(new RegisterResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);
        return responseBuilder.ok(new LoginResponse(token));
    }

}
