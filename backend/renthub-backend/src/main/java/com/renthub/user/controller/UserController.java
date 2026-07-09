package com.renthub.user.controller;

import com.renthub.security.service.AuthenticatedUserService;
import com.renthub.user.dto.UpdateProfileRequest;
import com.renthub.user.dto.UserProfileResponse;
import com.renthub.user.dto.UserResponse;
import com.renthub.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.renthub.user.dto.ChangePasswordRequest;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticatedUserService authenticatedUserService;

    @PreAuthorize("hasRole('ADMIN')")
@GetMapping
public List<UserResponse> getAllUsers() {
    return userService.getAllUsers();
}

    @PreAuthorize("hasRole('ADMIN')")
@GetMapping("/{id}")
public UserProfileResponse getUserById(
        @PathVariable Long id) {

    return userService.getUserById(id);
}

    @GetMapping("/me")
    public UserProfileResponse getCurrentProfile() {

        Long userId = authenticatedUserService.getCurrentUserId();

        return userService.getUserById(userId);
    }

    @PutMapping("/me")
    public UserProfileResponse updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        Long userId = authenticatedUserService.getCurrentUserId();

        return userService.updateProfile(userId, request);
    }

    @PostMapping(
            value = "/me/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserProfileResponse uploadProfileImage(
            @RequestParam("file") MultipartFile file) {

        Long userId = authenticatedUserService.getCurrentUserId();

        return userService.uploadProfileImage(userId, file);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deactivate")
    public String deactivateAccount() {

        Long userId = authenticatedUserService.getCurrentUserId();

        userService.deactivateAccount(userId);

        return "Account deactivated successfully";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public String deleteAccount() {

        Long userId = authenticatedUserService.getCurrentUserId();

        userService.deleteAccount(userId);

        return "Account deleted successfully";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
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