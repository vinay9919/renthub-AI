package com.renthub.user.mapper;

import com.renthub.user.dto.UserProfileResponse;
import com.renthub.user.dto.UserResponse;
import com.renthub.user.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());

        return response;
    }

    public static UserProfileResponse toProfileResponse(User user) {

        UserProfileResponse response = new UserProfileResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        response.setProfileImage(user.getProfileImage());
        response.setBio(user.getBio());

        response.setDateOfBirth(user.getDateOfBirth());
        response.setGender(user.getGender());

        response.setAddress(user.getAddress());
        response.setCity(user.getCity());
        response.setState(user.getState());
        response.setCountry(user.getCountry());
        response.setPincode(user.getPincode());

        response.setRole(user.getRole());
        response.setStatus(user.getStatus());

        response.setEmailVerified(user.isEmailVerified());
        response.setPhoneVerified(user.isPhoneVerified());
        response.setProfileCompleted(user.isProfileCompleted());

        response.setTotalBookings(user.getTotalBookings());
        response.setTotalListings(user.getTotalListings());
        response.setAverageRating(user.getAverageRating());

        return response;
    }

}