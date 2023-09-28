package com.vmo.demo.service;

import com.vmo.demo.entity.RefreshToken;

public interface RefreshTokenService {
    public RefreshToken createRefreshToken(String email);

    RefreshToken findByToken(String requestRefreshToken);

    boolean verifyExpiration(RefreshToken refreshToken);

    RefreshToken findByUserId(Integer id);
}
