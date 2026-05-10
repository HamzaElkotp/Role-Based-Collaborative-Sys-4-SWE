package com.example.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.authservice.dto.MembershipDTO;
import com.example.authservice.service.TeamRoleService;

import java.util.List;

@RestController
public class TeamRoleController {

    @Autowired
    private TeamRoleService teamRoleService;

    @GetMapping("/roles/{role_id}/permissions")
    public ResponseEntity<List<String>> getRolePermissions(@PathVariable Long role_id) {
        return ResponseEntity.ok(teamRoleService.getPermissionsByRole(role_id));
    }

    @GetMapping("/memberships/project/{project_id}/memberships/{user_id}/get-user-role")
    public ResponseEntity<String> getUserRole(@PathVariable Long project_id, @PathVariable Long user_id) {
        String role = teamRoleService.getUserRoleInProject(project_id, user_id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.badRequest().build();
    }

//    @DeleteMapping("/projects/{project_id}/memberships/{user_id}")
//    public ResponseEntity<Void> deleteMembership(@PathVariable Long project_id, @PathVariable Long user_id) {
//        teamRoleService.removeMember(project_id, user_id);
//        return ResponseEntity.noContent().build();
//    }

//    @PutMapping("/projects/{project_id}/memberships/{user_id}/change-role")
//    public ResponseEntity<MembershipDTO> changeRole(
//            @PathVariable Long project_id,
//            @PathVariable Long user_id,
//            @RequestParam Long newRoleId) {
//        return ResponseEntity.ok(teamRoleService.updateMemberRole(project_id, user_id, newRoleId));
//    }

    @GetMapping("/memberships/projects/{project_id}/members")
    public ResponseEntity<List<MembershipDTO>> getProjectMembers(@PathVariable Long project_id) {
        return ResponseEntity.ok(teamRoleService.getMembersByProject(project_id));
    }

    @PostMapping("/memberships/projects/{project_id}/memberships/add-new-member")
    public ResponseEntity<MembershipDTO> addMember(
            @PathVariable Long project_id,
            @RequestBody MembershipDTO membershipDTO) {
        return new ResponseEntity<>(teamRoleService.addMember(project_id, membershipDTO), HttpStatus.CREATED);
    }
}