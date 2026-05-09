package tasks_service.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tasks_service.demo.entity.Tasks;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByProjectId(Long projectId);
}