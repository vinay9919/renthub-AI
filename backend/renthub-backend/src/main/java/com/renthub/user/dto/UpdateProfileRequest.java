package com.renthub.user.dto;

import com.renthub.user.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid phone number"
    )
    private String phone;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String bio;

    private boolean emailNotifications;

    private boolean smsNotifications;

    private boolean marketingEmails;
}