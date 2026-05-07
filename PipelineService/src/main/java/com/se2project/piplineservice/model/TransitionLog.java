package com.se2project.piplineservice.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransitionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long updateId;

    @Enumerated(EnumType.STRING)
    private State fromState;

    @Enumerated(EnumType.STRING)
    private State toState;

    private LocalDateTime timestamp;

    // Getters & Setters
    public void setUpdateId(Long updateId) { this.updateId = updateId; }
    public void setFromState(State fromState) { this.fromState = fromState; }
    public void setToState(State toState) { this.toState = toState; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
