package com.renthub.user.controller;

import com.renthub.security.service.AuthenticatedUserService;
import com.renthub.user.dto.UpdateProfileRequest;
import com.renthub.user.dto.UserProfileResponse;
import com.renthub.user.dto.UserResponse;
import com.renthub.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.renthub.user.dto.ChangePasswordRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserProfileResponse getUserById(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }

    @GetMapping("/profile")
    public UserProfileResponse getCurrentProfile() {

        Long userId = authenticatedUserService.getCurrentUserId();

        return userService.getUserById(userId);
    }

    @PutMapping("/profile")
    public UserProfileResponse updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        Long userId = authenticatedUserService.getCurrentUserId();

        return userService.updateProfile(userId, request);
    }

    @PostMapping(
            value = "/profile/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserProfileResponse uploadProfileImage(
            @RequestParam("file") MultipartFile file) {

        Long userId = authenticatedUserService.getCurrentUserId();

        return userService.uploadProfileImage(userId, file);
    }

    @PostMapping("/deactivate")
    public String deactivateAccount() {

        Long userId = authenticatedUserService.getCurrentUserId();

        userService.deactivateAccount(userId);

        return "Account deactivated successfully";
    }

    @DeleteMapping
    public String deleteAccount() {

        Long userId = authenticatedUserService.getCurrentUserId();

        userService.deleteAccount(userId);

        return "Account deleted successfully";
    }

    @PostMapping("/change-password")
public String changePassword(
        @Valid
        @RequestBody
        ChangePasswordRequest request) {

    Long userId =
            authenticatedUserService.getCurrentUserId();

    userService.changePassword(userId, request);

    return "Password changed successfully";
}
}