package com.renthub.user.entity;

import com.renthub.common.entity.BaseEntity;
import com.renthub.user.model.Role;
import com.renthub.user.model.UserStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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

    private String profileImage;
}