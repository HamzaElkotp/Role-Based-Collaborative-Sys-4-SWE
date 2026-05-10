package com.example.project_service.repository;

import com.example.project_service.entities.Memberships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipsRepository extends JpaRepository<Memberships, Long> {
    List<Memberships> findByProjectId(Long projectId);
    Memberships findByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
}