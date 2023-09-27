package com.vmo.demo.dao;

import com.vmo.demo.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    @Query("select r from RefreshToken r where r.token = ?1")
    Optional<RefreshToken> findByToken(String token);
}
