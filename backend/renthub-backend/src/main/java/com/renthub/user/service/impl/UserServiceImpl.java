package com.renthub.user.service.impl;

import com.renthub.cloudinary.service.CloudinaryService;
import com.renthub.exception.EmailAlreadyExistsException;
import com.renthub.exception.PhoneAlreadyExistsException;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.user.dto.RegisterRequest;
import com.renthub.user.dto.UpdateProfileRequest;
import com.renthub.user.dto.UserProfileResponse;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.renthub.user.dto.ChangePasswordRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public UserProfileResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return UserMapper.toProfileResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!user.getPhone().equals(request.getPhone())
                && userRepository.existsByPhone(request.getPhone())) {

            throw new PhoneAlreadyExistsException("Phone number already registered");
        }

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());

        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setCountry(request.getCountry());
        user.setPincode(request.getPincode());

        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());

        user.setBio(request.getBio());

        user.setEmailNotifications(request.isEmailNotifications());
        user.setSmsNotifications(request.isSmsNotifications());
        user.setMarketingEmails(request.isMarketingEmails());

        User savedUser = userRepository.save(user);

        return UserMapper.toProfileResponse(savedUser);
    }

    @Override
    @Transactional
    public UserProfileResponse uploadProfileImage(Long userId, MultipartFile file) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        String imageUrl = cloudinaryService.uploadImage(file);

        user.setProfileImage(imageUrl);

        User savedUser = userRepository.save(user);

        return UserMapper.toProfileResponse(savedUser);
    }

    @Override
    @Transactional
    public void deactivateAccount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteAccount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setStatus(UserStatus.DELETED);

        userRepository.save(user);
    }
    @Override
@Transactional
public void changePassword(Long userId,
                           ChangePasswordRequest request) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    if (!passwordEncoder.matches(
            request.getCurrentPassword(),
            user.getPassword())) {

        throw new IllegalArgumentException(
                "Current password is incorrect");
    }

    if (!request.getNewPassword()
            .equals(request.getConfirmPassword())) {

        throw new IllegalArgumentException(
                "Passwords do not match");
    }

    if (passwordEncoder.matches(
            request.getNewPassword(),
            user.getPassword())) {

        throw new IllegalArgumentException(
                "New password must be different");
    }

    user.setPassword(
            passwordEncoder.encode(
                    request.getNewPassword()));

    userRepository.save(user);
}
}