package com.example.modifying_phase_service.mapper;



import com.example.modifying_phase_service.dto.response.UpdateFileResponse;
import com.example.modifying_phase_service.entity.UpdateFile;
import org.springframework.stereotype.Component;

@Component
public class UpdateFileMapper {

    public UpdateFileResponse toResponse(UpdateFile entity) {
        return UpdateFileResponse.builder()
                .id(entity.getId())
                .updateId(entity.getCodeUpdate().getId())
                .fileName(entity.getFileName())
                .fileUrl(entity.getFileUrl())
                .fileType(entity.getFileType())
                .uploadedAt(entity.getUploadedAt())
                .build();
    }
}