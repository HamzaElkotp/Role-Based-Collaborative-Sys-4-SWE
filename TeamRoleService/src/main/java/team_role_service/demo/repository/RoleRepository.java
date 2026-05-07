package team_role_service.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_role_service.demo.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
}