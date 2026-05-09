package com.example.modifying_phase_service.dto.request;



import com.example.modifying_phase_service.enums.UpdateStatus;
import com.example.modifying_phase_service.enums.VersionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUpdateRequest {

    @NotNull(message = "Developer ID is required")
    private Long developerId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Version type is required")
    private VersionType versionType;

    @NotNull(message = "Status is required")
    private UpdateStatus newStatus;
}