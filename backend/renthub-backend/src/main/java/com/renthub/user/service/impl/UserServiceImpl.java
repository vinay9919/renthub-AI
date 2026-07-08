package com.renthub.user.service.impl;

import com.renthub.exception.PhoneAlreadyExistsException;
import com.renthub.exception.EmailAlreadyExistsException;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.user.dto.RegisterRequest;
import com.renthub.user.dto.UserResponse;
import com.renthub.user.entity.User;
import com.renthub.user.mapper.UserMapper;
import com.renthub.user.model.Role;
import com.renthub.user.model.UserStatus;
import com.renthub.user.repository.UserRepository;
import com.renthub.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new PhoneAlreadyExistsException("Phone number already registered");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.CUSTOMER);

        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

}