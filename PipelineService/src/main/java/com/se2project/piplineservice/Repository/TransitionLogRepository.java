package com.se2project.piplineservice.Repository;

import com.se2project.piplineservice.model.TransitionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionLogRepository extends JpaRepository<TransitionLog, Long> {
}
