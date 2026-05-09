package tasks_service.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tasks_service.demo.entity.TaskPriority;

public interface TaskPriorityRepository extends JpaRepository<TaskPriority, Long> {
}