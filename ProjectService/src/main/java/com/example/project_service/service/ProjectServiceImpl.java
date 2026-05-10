package com.example.project_service.service;



import com.example.project_service.dto.ProjectRequest;
import com.example.project_service.dto.ProjectResponse;
import com.example.project_service.exception.ProjectNotFoundException;
import com.example.project_service.model.Project;
import com.example.project_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

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
    public ProjectResponse updateProject(Long id, ProjectRequest request, String userId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        if (!project.getOwnerId().toString().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner!");
        }

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        // project.setOwnerId(request.getOwnerId());

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
}