package com.se2project.piplineservice.Repository;
package com.se2project.piplineservice.model.TransitionLog;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransitionLogRepository extends JpaRepository<TransitionLog, Long> {
}

