package com.example.modifying_phase_service.repository;


import com.example.modifying_phase_service.entity.UpdateFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateFileRepository extends JpaRepository<UpdateFile, Long> {
    List<UpdateFile> findAllByCodeUpdateId(Long updateId);
}