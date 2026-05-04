package com.oa.InvitationService.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.InvitationService.entities.Invitation;
import com.oa.InvitationService.repos.InvitationRepo;

import jakarta.transaction.Transactional;

@Service
public class InvitationService {
    private static final String INV_BASE_ROUTE = "http://localhost:8080/invitations/";
    @Autowired
    private InvitationRepo invitationRepo;

    public List<Invitation> getAll() {
        return invitationRepo.findAll();
    }

    public Optional<Invitation> getInvById(Long id) {
        return invitationRepo.findById(id);
    }

    @Transactional
    public Invitation save(Invitation invitation) {
        Invitation savedInv = invitationRepo.save(invitation);

        String newInvLink = INV_BASE_ROUTE + savedInv.getId();

        savedInv.setInvitationLink(newInvLink);

        return invitationRepo.save(savedInv);

    }
}
