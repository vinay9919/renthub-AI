package com.renthub.auth.controller;

import com.renthub.auth.dto.LoginRequest;
import com.renthub.auth.dto.LoginResponse;
import com.renthub.auth.service.AuthService;
import com.renthub.user.dto.RegisterRequest;
import com.renthub.user.dto.UserResponse;
import com.renthub.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(
            @Valid
            @RequestBody RegisterRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}