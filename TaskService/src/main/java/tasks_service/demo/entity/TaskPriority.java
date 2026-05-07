package tasks_service.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "task_priority")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String priorityName;


    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL)
    private List<Tasks> tasks;
}