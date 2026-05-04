package com.oa.InvitationService.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.oa.InvitationService.entities.Invitation;
import com.oa.InvitationService.services.InvitationService;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



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

    @PostMapping("/invitations/send")
    public Invitation sendInvitation(@Valid @RequestBody Invitation invitation) {
        return invitationService.save(invitation);
    }
    
}