package com.se2project.piplineservice.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PipelineUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String createdBy;

    @Enumerated(EnumType.STRING)
    private State currentState;

    private boolean testsPassed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters & Setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public State getCurrentState() { return currentState; }
    public void setCurrentState(State currentState) { this.currentState = currentState; }

    public boolean isTestsPassed() { return testsPassed; }
    public void setTestsPassed(boolean testsPassed) { this.testsPassed = testsPassed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
