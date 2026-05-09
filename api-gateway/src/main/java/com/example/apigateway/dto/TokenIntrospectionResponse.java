package com.example.apigateway.dto;

import java.util.List;

public record TokenIntrospectionResponse(boolean active, String userId, List<String> roles) {
}
