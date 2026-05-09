package tasks_service.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private Long creatorId;
    private Long statusId;
    private String statusName;
    private Long priorityId;
    private String priorityName;
    private LocalDateTime createdAt;
}