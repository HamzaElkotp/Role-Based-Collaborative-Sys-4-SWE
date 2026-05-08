package com.oa.NotificationService.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto implements Serializable {

    @NotNull(message = "Type is required")
    private Long type = 1L;

    @NotNull(message = "Role ID is required")
    private Long roleId;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotBlank(message = "Title is required")
    private String title;

    private String message; // Optional
}