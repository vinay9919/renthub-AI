package com.renthub.security.jwt;

public interface JwtService {

    String generateAccessToken(String email);

    String generateRefreshToken(String email);

    String extractUsername(String token);

    boolean validateToken(String token);
    
    boolean isTokenExpired(String token);
}