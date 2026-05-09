package com.example.modifying_phase_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "update_history", indexes = {
    @Index(name = "idx_update_id_history", columnList = "update_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id", nullable = false)
    private CodeUpdate codeUpdate;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}