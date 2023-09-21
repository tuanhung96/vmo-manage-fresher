package com.vmo.manage_fresher.service;

import com.vmo.manage_fresher.entity.RefreshToken;

public interface RefreshTokenService {
    public RefreshToken createRefreshToken(String email);

    RefreshToken findByToken(String requestRefreshToken);

    boolean verifyExpiration(RefreshToken refreshToken);
}
