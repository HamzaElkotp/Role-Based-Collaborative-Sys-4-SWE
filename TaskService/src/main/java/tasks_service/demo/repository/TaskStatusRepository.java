package tasks_service.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tasks_service.demo.entity.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
}