package com.example.modifying_phase_service.mapper;



import com.example.modifying_phase_service.dto.response.CodeUpdateResponse;
import com.example.modifying_phase_service.entity.CodeUpdate;
import org.springframework.stereotype.Component;

@Component
public class CodeUpdateMapper {

    public CodeUpdateResponse toResponse(CodeUpdate entity) {
        return CodeUpdateResponse.builder()
                .id(entity.getId())
                .projectId(entity.getProjectId())
                .developerId(entity.getDeveloperId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .versionType(entity.getVersionType())
                .oldStatus(entity.getOldStatus())
                .newStatus(entity.getNewStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}