package com.renthub.user.service;

import com.renthub.user.dto.RegisterRequest;
import com.renthub.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

}