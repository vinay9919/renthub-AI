package com.renthub.auth.service;

import com.renthub.auth.dto.LoginRequest;
import com.renthub.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}