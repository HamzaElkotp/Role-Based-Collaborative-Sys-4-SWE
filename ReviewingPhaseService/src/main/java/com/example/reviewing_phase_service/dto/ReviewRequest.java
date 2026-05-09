package com.example.reviewing_phase_service.dto;

import com.example.reviewing_phase_service.enums.ReviewStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
public class ReviewRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Reviewer ID is required")
    private Long reviewerId;

    private ReviewStatus status;
    private String comment;
    private Integer score;
}

