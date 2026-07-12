package com.renthub.user.dto;

import com.renthub.user.model.Gender;
import com.renthub.user.model.Role;
import com.renthub.user.model.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserProfileResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String profileImage;

    private String bio;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private Role role;

    private UserStatus status;

    private boolean emailVerified;

    private boolean phoneVerified;

    private boolean profileCompleted;

    private Integer totalBookings;

    private Integer totalListings;

    private Double averageRating;
}