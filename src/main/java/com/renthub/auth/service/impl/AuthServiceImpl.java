package com.renthub.auth.service.impl;

import com.renthub.auth.dto.LoginRequest;
import com.renthub.auth.dto.LoginResponse;
import com.renthub.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResponse login(LoginRequest request) {

        return null;

    }

}