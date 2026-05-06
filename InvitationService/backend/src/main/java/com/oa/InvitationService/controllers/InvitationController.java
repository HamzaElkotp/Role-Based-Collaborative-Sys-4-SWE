package com.oa.InvitationService.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.oa.InvitationService.entities.Invitation;
import com.oa.InvitationService.services.InvitationService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class InvitationController {

    @Autowired
    private InvitationService invitationService;
    
    @GetMapping("/")
    public String getHomeRoute() {
        return "Server is up & running";
    }

    @GetMapping("/invitations/{id}")
    public Optional<Invitation> getInvById(@PathVariable Long id) {
        return invitationService.getInvById(id);
    }

    @GetMapping("/invitations")
    public List<Invitation> getMethodName(@RequestParam String email) {
        return invitationService.getUserInvitations(email);
    }
    

    @PostMapping("/invitations/send")
    public Invitation sendInvitation(@Valid @RequestBody Invitation invitation) {
        return invitationService.send(invitation);
    }

    @PutMapping("/invitations/{id}/resend")
    public Invitation resendInvitation(@PathVariable Long id) {
        return invitationService.resend(id);
    }

    @PutMapping("/invitations/{id}/cancel")
    public Invitation cancelInvitation(@PathVariable Long id) {
        return invitationService.cancel(id);
    }

    @PutMapping("/invitations/{id}/accept")
    public Invitation acceptInvitation(@PathVariable Long id) {
        return invitationService.accept(id);
    }
    
}