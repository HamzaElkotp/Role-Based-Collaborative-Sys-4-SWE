package com.example.project_service.service;

import com.example.project_service.dto.MembershipDTO;
import com.example.project_service.dto.ProjectRequest;
import com.example.project_service.dto.ProjectResponse;
import com.example.project_service.entities.Memberships;
import com.example.project_service.exception.ProjectNotFoundException;
import com.example.project_service.entities.Project;
import com.example.project_service.repository.MembershipsRepository;
import com.example.project_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private final MembershipsRepository membershipRepository;
    @Autowired
    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request, String authenticatedUserId) {
    Project project = Project.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .ownerId(Long.valueOf(authenticatedUserId)) // Use the ID from the header!
            .build();

    Project saved = projectRepository.save(project);
    return mapToResponse(saved);
}

    @Override
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        return mapToResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponse> getProjectsByOwner(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, String authUserId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        if (!project.getOwnerId().toString().equals(authUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner!");
        }

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());

        Project updated = projectRepository.save(project);
        return mapToResponse(updated);
    }

    @Override
    public void deleteProject(Long id, String userId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        if (!project.getOwnerId().toString().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner!");
        }

        projectRepository.delete(project);
    }

    @Override
    public String getUserRoleInProject(Long projectId, Long userId) {
        Memberships m = membershipRepository.findByProjectIdAndUserId(projectId, userId);
        if (m != null) {
            return m.getRole();
        }
        return null;
    }

    @Override
    public MembershipDTO addMember(Long projectId, MembershipDTO membershipDTO, String authUserId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        // add ownership check

        if (!project.getOwnerId().toString().equals(authUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner!");
        }

        // add email availability check (Ask auth)

        Memberships membership = new Memberships();
        membership.setProjectId(projectId);
        membership.setUserId(Long.valueOf(membershipDTO.getUser_id()));
        membership.setRole(membershipDTO.getRole());
        return convertToDTO(membershipRepository.save(membership));
    }

    @Override
    public List<MembershipDTO> getProjectMembers(Long projectId) {
        return membershipRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // ── private helper ──────────────────────────────────────────
    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .ownerId(project.getOwnerId())
                .status(project.getStatus())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }

    private MembershipDTO convertToDTO(Memberships m) {
        MembershipDTO dto = new MembershipDTO();
        dto.setId(m.getId());
        dto.setProject_id(m.getProjectId());
        dto.setUser_id(String.valueOf(m.getUserId()));
        dto.setRole(m.getRole());
        dto.setJoined_at(m.getJoined_at());
        return dto;
    }
}