package com.example.modifying_phase_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "update_tasks", indexes = {
    @Index(name = "idx_update_id_task", columnList = "update_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id", nullable = false)
    private CodeUpdate codeUpdate;

    @Column(name = "task_id", nullable = false)
    private Long taskId;
}
