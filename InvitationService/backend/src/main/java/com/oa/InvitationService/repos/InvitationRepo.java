package com.oa.InvitationService.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oa.InvitationService.entities.Invitation;

@Repository
public interface InvitationRepo extends JpaRepository<Invitation, Long> {
    
}
