package com.vmo.manage_fresher.service;

import com.vmo.manage_fresher.dao.RefreshTokenRepository;
import com.vmo.manage_fresher.dao.UserRepository;
import com.vmo.manage_fresher.entity.Fresher;
import com.vmo.manage_fresher.entity.RefreshToken;
import com.vmo.manage_fresher.exception.FresherNotFoundException;
import com.vmo.manage_fresher.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenExpirationMs;
    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findByEmail(email));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken findByToken(String token) {
        Optional<RefreshToken> result = refreshTokenRepository.findByToken(token);
        RefreshToken refreshToken = null;

        if (result.isPresent()) {
            refreshToken = result.get();
        } else {
            throw new TokenRefreshException("Did not find refresh token with token - " + token);
        }
        return refreshToken;
    }

    public boolean verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            return false;
        }
        return true;
    }
}
