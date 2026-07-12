package com.renthub.user.service;

import com.renthub.user.dto.ChangePasswordRequest;
import com.renthub.user.dto.RegisterRequest;
import com.renthub.user.dto.UpdateProfileRequest;
import com.renthub.user.dto.UserProfileResponse;
import com.renthub.user.dto.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserProfileResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserProfileResponse updateProfile(Long userId,
                                      UpdateProfileRequest request);

    UserProfileResponse uploadProfileImage(Long userId,
                                           MultipartFile file);

    void changePassword(Long userId, ChangePasswordRequest request);

    void deactivateAccount(Long userId);

    void deleteAccount(Long userId);

}