package com.vmo.manage_fresher.controller;

import com.vmo.manage_fresher.config.JwtTokenUtil;
import com.vmo.manage_fresher.entity.RefreshToken;
import com.vmo.manage_fresher.entity.User;
import com.vmo.manage_fresher.exception.InvalidUserException;
import com.vmo.manage_fresher.exception.TokenRefreshException;
import com.vmo.manage_fresher.exception.UserDisabledException;
import com.vmo.manage_fresher.model.request.AuthenticationRequest;
import com.vmo.manage_fresher.model.response.AuthenticationResponse;
import com.vmo.manage_fresher.model.request.TokenRefreshRequest;
import com.vmo.manage_fresher.service.RefreshTokenService;
import com.vmo.manage_fresher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                    UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt, refreshToken.getToken()));
    }

    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new UserDisabledException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new InvalidUserException("INVALID_CREDENTIALS");
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);

        if(refreshTokenService.verifyExpiration(refreshToken)) {
            User user = refreshToken.getUser();
            String token = jwtTokenUtil.generateToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                            Arrays.asList(new SimpleGrantedAuthority("NO_ROLE"))));
            return ResponseEntity.ok(new AuthenticationResponse(token, requestRefreshToken));
        } else {
            throw new TokenRefreshException("Refresh token was expired. Please make a new sign in request");
        }
    }
}
