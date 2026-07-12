package com.renthub.refresh.service;

import com.renthub.refresh.entity.RefreshToken;
import com.renthub.user.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void deleteRefreshToken(User user);

}