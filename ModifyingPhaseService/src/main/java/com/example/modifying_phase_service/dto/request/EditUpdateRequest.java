package com.example.modifying_phase_service.dto.request;


import com.example.modifying_phase_service.enums.UpdateStatus;
import com.example.modifying_phase_service.enums.VersionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditUpdateRequest {

    @NotNull(message = "ID is required")
    private Long id;

    private String title;
    private String description;
    private VersionType versionType;
    private UpdateStatus newStatus;
}