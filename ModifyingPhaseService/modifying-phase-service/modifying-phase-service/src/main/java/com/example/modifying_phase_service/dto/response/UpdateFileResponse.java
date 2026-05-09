package com.example.modifying_phase_service.dto.response;



import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class UpdateFileResponse {
    private Long id;
    private Long updateId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private LocalDateTime uploadedAt;
}