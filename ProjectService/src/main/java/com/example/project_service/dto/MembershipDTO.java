package com.example.project_service.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MembershipDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long project_id;
    private String user_id;
    private String role;
    private LocalDateTime joined_at;
}