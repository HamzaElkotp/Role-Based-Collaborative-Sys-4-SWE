// entity/CodeUpdate.java
package com.example.modifying_phase_service.entity;

import com.example.modifying_phase_service.enums.UpdateStatus;
import com.example.modifying_phase_service.enums.VersionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "code_updates", indexes = {
    @Index(name = "idx_project_id", columnList = "project_id"),
    @Index(name = "idx_developer_id", columnList = "developer_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CodeUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "developer_id", nullable = false)
    private Long developerId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "version_type", nullable = false)
    private VersionType versionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status")
    private UpdateStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private UpdateStatus newStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "codeUpdate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UpdateFile> files;

    @OneToMany(mappedBy = "codeUpdate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UpdateTask> tasks;

    @OneToMany(mappedBy = "codeUpdate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UpdateHistory> history;
}
