package com.example.modifying_phase_service.dto.response;



import com.example.modifying_phase_service.enums.UpdateStatus;
import com.example.modifying_phase_service.enums.VersionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class CodeUpdateResponse {
    private Long id;
    private Long projectId;
    private Long developerId;
    private String title;
    private String description;
    private VersionType versionType;
    private UpdateStatus oldStatus;
    private UpdateStatus newStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}