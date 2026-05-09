package com.example.reviewing_phase_service.model;

import com.example.reviewing_phase_service.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review_history")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReviewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "old_review_id")
    private Long oldReviewId;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private ReviewStatus newStatus;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @PrePersist
    protected void onCreate() {
        this.changedAt = LocalDateTime.now();
    }
}
