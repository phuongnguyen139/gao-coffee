package com.example.demo.ui.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.RefreshToken;
import com.example.demo.payload.TokenRefreshRequest;
import com.example.demo.payload.TokenRefreshResponse;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.RefreshTokenService;

@RestController
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthController(JwtTokenProvider tokenProvider, RefreshTokenService refreshTokenService) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(requestRefreshToken);

        if (refreshTokenOpt.isPresent()) {
            RefreshToken refreshToken = refreshTokenOpt.get();
            try {
                refreshTokenService.verifyExpiration(refreshToken);
                String newToken = tokenProvider.generateTokenFromUsername(refreshToken.getUsername());
                return ResponseEntity.ok(new TokenRefreshResponse(newToken, requestRefreshToken));
            } catch (Exception e) {
                return ResponseEntity.status(401).body("Refresh token was expired. Please make a new signin request");
            }
        } else {
            return ResponseEntity.status(404).body("Refresh token is not in database!");
        }
    }
}