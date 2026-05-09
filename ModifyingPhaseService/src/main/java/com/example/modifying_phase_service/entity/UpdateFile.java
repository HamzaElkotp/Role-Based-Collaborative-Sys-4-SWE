// entity/UpdateFile.java
package com.example.modifying_phase_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "update_file", indexes = {
    @Index(name = "idx_update_id_file", columnList = "update_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id", nullable = false)
    private CodeUpdate codeUpdate;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;
}
