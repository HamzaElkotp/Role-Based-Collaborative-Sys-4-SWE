package com.example.project_service.controller;

import com.example.project_service.dto.MembershipDTO;
import com.example.project_service.dto.ProjectRequest;
import com.example.project_service.dto.ProjectResponse;
import com.example.project_service.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request,
            @RequestHeader("X-User-Id") String userId) {

        // Pass the userId into your service method
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.createProject(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByOwner(
            @PathVariable Long ownerId) {
        return ResponseEntity.ok(projectService.getProjectsByOwner(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request,
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(projectService.updateProject(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") String userId) { // Grab the ID from header

        projectService.deleteProject(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{project_id}/user/{user_id}/role")
    public ResponseEntity<String> getUserRole(@PathVariable Long project_id, @PathVariable Long user_id) {
        String role = projectService.getUserRoleInProject(project_id, user_id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{project_id}/add-member")
    public ResponseEntity<MembershipDTO> addMember(       // return the DTO, not String
          @PathVariable Long project_id,                // ← missing annotation
          @RequestBody MembershipDTO membershipDTO,      // ← missing annotation
          @RequestHeader("X-User-Id") String userId) {
        MembershipDTO result = projectService.addMember(project_id, membershipDTO, userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{project_id}/members")
    public ResponseEntity<List<MembershipDTO>> getProjectMembers(@PathVariable Long project_id) {
        return ResponseEntity.ok(projectService.getProjectMembers(project_id));
    }
}
