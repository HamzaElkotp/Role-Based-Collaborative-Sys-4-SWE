package team_role_service.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_role_service.demo.entity.Memberships;
import java.util.List;

@Repository
public interface MembershipsRepository extends JpaRepository<Memberships, Long> {
    List<Memberships> findByProjectId(Long projectId);
    Memberships findByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
}