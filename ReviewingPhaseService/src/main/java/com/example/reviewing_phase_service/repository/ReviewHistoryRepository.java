package com.example.reviewing_phase_service.repository;

import com.example.reviewing_phase_service.model.ReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewHistoryRepository extends JpaRepository<ReviewHistory, Long> {
}
