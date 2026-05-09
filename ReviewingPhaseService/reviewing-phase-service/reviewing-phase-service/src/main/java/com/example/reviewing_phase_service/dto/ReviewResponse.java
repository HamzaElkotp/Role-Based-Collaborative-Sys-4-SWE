package com.example.reviewing_phase_service.dto;

import com.example.reviewing_phase_service.enums.ReviewStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;
    private Long projectId;
    private Long reviewerId;
    private ReviewStatus status;
    private String comment;
    private Integer score;
    private LocalDateTime createdAt;
}