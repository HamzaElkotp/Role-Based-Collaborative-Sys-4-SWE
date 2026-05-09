package com.example.modifying_phase_service.repository;

import com.example.modifying_phase_service.entity.UpdateTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UpdateTaskRepository extends JpaRepository<UpdateTask, Long> {
    Optional<UpdateTask> findByCodeUpdateIdAndTaskId(Long updateId, Long taskId);
    List<UpdateTask> findAllByCodeUpdateId(Long updateId);
}