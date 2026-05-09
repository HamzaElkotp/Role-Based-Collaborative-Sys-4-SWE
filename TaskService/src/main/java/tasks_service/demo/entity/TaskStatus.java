package tasks_service.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "task_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String statusName;


    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Tasks> tasks;
}