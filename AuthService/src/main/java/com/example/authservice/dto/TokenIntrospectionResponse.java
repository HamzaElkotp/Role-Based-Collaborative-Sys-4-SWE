package com.example.authservice.dto;

import java.util.List;

public record TokenIntrospectionResponse(boolean active, String userId) {
}
