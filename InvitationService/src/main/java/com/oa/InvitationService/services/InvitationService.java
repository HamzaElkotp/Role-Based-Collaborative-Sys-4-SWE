package com.oa.InvitationService.services;

import com.oa.InvitationService.client.NotificationServiceClient;
import com.oa.InvitationService.dto.InternalNotificationRequest;
import com.oa.InvitationService.entities.Invitation;
import com.oa.InvitationService.repos.InvitationRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvitationService {
    private final InvitationRepo invitationRepo;
    private final NotificationServiceClient notificationServiceClient;
    private final String invitationBaseRoute;

    public InvitationService(
            InvitationRepo invitationRepo,
            NotificationServiceClient notificationServiceClient,
            @Value("${app.invitation.public-base-url}") String invitationBaseRoute) {
        this.invitationRepo = invitationRepo;
        this.notificationServiceClient = notificationServiceClient;
        this.invitationBaseRoute = invitationBaseRoute;
    }

    public List<Invitation> getAll() {
        return invitationRepo.findAll();
    }

    public Optional<Invitation> getInvById(Long id) {
        return invitationRepo.findById(id);
    }

    public List<Invitation> getUserInvitations(String userEmail) {
        return invitationRepo.findByEmail(userEmail);
    }

    public List<Invitation> getInvitationsByProjectId(Long id) {
        return invitationRepo.findByProjectId(id);
    }

    @Transactional
    public Invitation send(Invitation invitation) {
        Invitation savedInv = invitationRepo.save(invitation);

        String newInvLink = invitationBaseRoute + savedInv.getId();

        savedInv.setInvitationLink(newInvLink);

        notificationServiceClient.createInternalNotification(new InternalNotificationRequest(
                savedInv.getProjectId(),
                savedInv.getInviteeRoleId(),
                "New Project Invitation",
                "Invitation sent to " + savedInv.getEmail()
        ));

        return invitationRepo.save(savedInv);

    }

    @Transactional
    public Invitation resend(Long id) {
        // 1. Find the existing invitation
        Invitation existingInv = invitationRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitation with id: " + id + " was not found"));

        validateStatus(existingInv, "ACCEPTED");

        // 2. Update the resentAt and status
        existingInv.setResentAt(LocalDateTime.now());
        existingInv.setStatus("PENDING");

        // 3. Save the updates
        Invitation updatedInv = invitationRepo.save(existingInv);

        // TODO - send an email/notification with the invitation details


        return updatedInv;
    }

    @Transactional
    public Invitation cancel(Long id) {
        Invitation existingInv = invitationRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitation with id: " + id + " was not found"));

        checkAndApplyExpiration(existingInv);

        validateStatus(existingInv, "ACCEPTED", "EXPIRED", "CANCELED");

        existingInv.setStatus("CANCELED");

        invitationRepo.save(existingInv);

        return existingInv;
    }

    @Transactional
    public Invitation accept(Long id) {
        Invitation existingInv = invitationRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invitation with id: " + id + " was not found"));

        checkAndApplyExpiration(existingInv);
        
        validateStatus(existingInv, "ACCEPTED", "EXPIRED", "CANCELED");

        // 2. Determine reference date
        LocalDateTime referenceDate = (existingInv.getResentAt() != null) 
                                    ? existingInv.getResentAt() 
                                    : existingInv.getCreatedAt();

        // 3. Check for NEW expiration (if it hasn't been marked yet)
        if (referenceDate.isBefore(LocalDateTime.now().minusDays(13))) {
            existingInv.setStatus("EXPIRED");
            invitationRepo.save(existingInv);
            throw new IllegalStateException("This invitation has expired.");
        }

        existingInv.setStatus("ACCEPTED");
        return invitationRepo.save(existingInv);
    }

    private void validateStatus(Invitation inv, String... forbiddenStatuses) {
        String currentStatus = inv.getStatus();
        for (String forbidden : forbiddenStatuses) {
            if (forbidden.equals(currentStatus)) {
                throw new IllegalStateException("Cannot perform this action because the invitation status is " + currentStatus);
            }
        }
    }

    private void checkAndApplyExpiration(Invitation inv) {
    // If it's already terminal (Accepted/Canceled), don't bother checking dates
    if ("ACCEPTED".equals(inv.getStatus()) || "CANCELED".equals(inv.getStatus())) {
        return;
    }

    LocalDateTime refDate = (inv.getResentAt() != null) ? inv.getResentAt() : inv.getCreatedAt();

    if (refDate.isBefore(LocalDateTime.now().minusDays(13))) {
        inv.setStatus("EXPIRED");
        invitationRepo.save(inv); 
    }
}
}
