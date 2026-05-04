package com.oa.InvitationService.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.oa.InvitationService.entities.Invitation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class InvitationController {
    
    @GetMapping("/{id}")
    public Integer getInvById(@PathVariable Integer id) {
        return id;
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Invitation> getInvById(@PathVariable Integer id) {
    //     return invitationRepository.findById(id)
    //             .map(invitation -> ResponseEntity.ok(invitation))
    //             .orElse(ResponseEntity.notFound().build());
    // }
    
}