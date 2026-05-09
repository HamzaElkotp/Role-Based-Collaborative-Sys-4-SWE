package com.example.modifying_phase_service.repository;


import com.example.modifying_phase_service.entity.CodeUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeUpdateRepository extends JpaRepository<CodeUpdate, Long> {

    List<CodeUpdate> findAllByProjectId(Long projectId);

    Optional<CodeUpdate> findByIdAndProjectId(Long id, Long projectId);

    @Query("SELECT c FROM CodeUpdate c WHERE c.id = :id AND c.projectId = :projectId")
    Optional<CodeUpdate> findUpdateByIdAndProject(Long id, Long projectId);
}