package com.example.modifying_phase_service.repository;



import com.example.modifying_phase_service.entity.UpdateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateHistoryRepository extends JpaRepository<UpdateHistory, Long> {
}
