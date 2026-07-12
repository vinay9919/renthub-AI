package com.renthub.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String role;

    private String status;

    private Boolean emailVerified;

    private Boolean phoneVerified;

}