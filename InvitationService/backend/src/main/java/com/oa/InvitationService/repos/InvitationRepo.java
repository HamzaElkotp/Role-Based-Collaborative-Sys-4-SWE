package com.oa.InvitationService.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oa.InvitationService.entities.Invitation;

@Repository
public interface InvitationRepo extends JpaRepository<Invitation, Long> {
    List<Invitation> findByEmail(String email);

    List<Invitation> findByProjectId(Long projectId);
}
