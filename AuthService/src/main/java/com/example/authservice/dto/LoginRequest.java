package com.example.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Size(min = 3, max = 100) String email,
        @NotBlank @Size(min = 8, max = 255) String password
) {
}
