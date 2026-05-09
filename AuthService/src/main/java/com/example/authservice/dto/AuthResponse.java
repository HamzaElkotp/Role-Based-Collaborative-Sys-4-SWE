package com.example.authservice.dto;

import java.util.List;

public record AuthResponse(
        String accessToken,
        String tokenType,
        Long expiresInSeconds,
        String userId,
        String email
) {
}
