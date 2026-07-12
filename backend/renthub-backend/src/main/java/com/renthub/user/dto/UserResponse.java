package com.renthub.user.dto;

import com.renthub.user.model.Role;
import com.renthub.user.model.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private Role role;

    private UserStatus status;

    private String profileImage;

}