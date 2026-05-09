package com.example.reviewing_phase_service.repository;

import com.example.reviewing_phase_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProjectId(Long projectId);
}