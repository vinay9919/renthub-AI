package com.renthub.refresh.service.impl;

import com.renthub.refresh.entity.RefreshToken;
import com.renthub.refresh.repository.RefreshTokenRepository;
import com.renthub.refresh.service.RefreshTokenService;
import com.renthub.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Override
    public RefreshToken createRefreshToken(User user) {

        repository.findByUser(user)
                .ifPresent(repository::delete);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);

        refreshToken.setToken(java.util.UUID.randomUUID().toString());

        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));

        refreshToken.setRevoked(false);

        return repository.save(refreshToken);

    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = repository.findByToken(token)

                .orElseThrow(() ->
                        new RuntimeException("Refresh Token not found"));

        if (refreshToken.isRevoked()) {

            throw new RuntimeException("Refresh Token revoked");

        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Refresh Token expired");

        }

        return refreshToken;

    }

    @Override
    public void deleteRefreshToken(User user) {

        repository.deleteByUser(user);

    }

}