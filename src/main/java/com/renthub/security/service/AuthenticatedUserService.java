package com.renthub.security.service;

import com.renthub.user.entity.User;

public interface AuthenticatedUserService {

    User getCurrentUser();

    Long getCurrentUserId();

}