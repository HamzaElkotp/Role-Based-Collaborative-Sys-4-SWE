package com.example.modifying_phase_service.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadFileRequest {

    @NotNull(message = "Update ID is required")
    private Long updateId;

    @NotBlank(message = "File name is required")
    private String fileName;

    @NotBlank(message = "File URL is required")
    private String fileUrl;

    private String fileType;
}