package com.example.project_service.service;

import com.example.project_service.dto.ProjectRequest;
import com.example.project_service.dto.ProjectResponse;
import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request, String authenticatedUserId);
    ProjectResponse getProjectById(Long id);
    List<ProjectResponse> getAllProjects();
    List<ProjectResponse> getProjectsByOwner(Long ownerId);
    ProjectResponse updateProject(Long id, ProjectRequest request, String userId);
    void deleteProject(Long id, String userid);
}