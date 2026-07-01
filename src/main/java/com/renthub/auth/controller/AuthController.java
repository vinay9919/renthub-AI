package com.renthub.auth.controller;
import jakarta.validation.Valid;
import com.renthub.user.dto.RegisterRequest;
import com.renthub.user.dto.UserResponse;
import com.renthub.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
public UserResponse register(
        @Valid @RequestBody RegisterRequest request) {

    return userService.register(request);
}

}