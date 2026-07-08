package com.renthub.user.entity;

import com.renthub.common.entity.BaseEntity;
import com.renthub.user.model.Gender;
import com.renthub.user.model.Role;
import com.renthub.user.model.UserStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    @Column(nullable = false, unique = true)
    private String phone;

    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // --------------------------
    // Profile
    // --------------------------

    private String profileImage;

    @Column(length = 500)
    private String bio;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // --------------------------
    // Address
    // --------------------------

    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;

    // --------------------------
    // Verification
    // --------------------------

    private boolean emailVerified = false;

    private boolean phoneVerified = false;

    private boolean profileCompleted = false;

    // --------------------------
    // Login Security
    // --------------------------

    private Integer failedLoginAttempts = 0;

    private LocalDateTime lockUntil;

    private LocalDateTime lastLogin;

    // --------------------------
    // Notification Preferences
    // --------------------------

    private boolean emailNotifications = true;

    private boolean smsNotifications = true;

    private boolean marketingEmails = false;

    // --------------------------
    // User Statistics
    // --------------------------

    private Integer totalBookings = 0;

    private Integer totalListings = 0;

    private Double averageRating = 0.0;
}