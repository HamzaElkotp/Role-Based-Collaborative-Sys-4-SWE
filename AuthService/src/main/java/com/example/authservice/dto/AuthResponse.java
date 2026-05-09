package com.example.authservice.dto;

import java.util.List;

public record AuthResponse(
        String accessToken,
        String userId,
        String email
) {
}
