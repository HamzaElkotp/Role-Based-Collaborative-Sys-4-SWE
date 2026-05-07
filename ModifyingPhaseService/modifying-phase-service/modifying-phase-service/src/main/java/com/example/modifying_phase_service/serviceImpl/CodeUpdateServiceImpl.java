package com.example.modifying_phase_service.serviceImpl;



import com.example.modifying_phase_service.dto.request.CreateUpdateRequest;
import com.example.modifying_phase_service.dto.request.EditUpdateRequest;
import com.example.modifying_phase_service.dto.response.CodeUpdateResponse;
import com.example.modifying_phase_service.entity.CodeUpdate;
import com.example.modifying_phase_service.entity.UpdateHistory;
import com.example.modifying_phase_service.entity.UpdateTask;
import com.example.modifying_phase_service.exception.ResourceNotFoundException;
import com.example.modifying_phase_service.mapper.CodeUpdateMapper;
import com.example.modifying_phase_service.repository.CodeUpdateRepository;
import com.example.modifying_phase_service.repository.UpdateTaskRepository;
import com.example.modifying_phase_service.repository.UpdateHistoryRepository;
import com.example.modifying_phase_service.service.CodeUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeUpdateServiceImpl implements CodeUpdateService {

    private final CodeUpdateRepository codeUpdateRepository;
    private final UpdateHistoryRepository updateHistoryRepository;
    private final CodeUpdateMapper codeUpdateMapper;
    private final UpdateTaskRepository UpdateTaskRepository;

    @Override
    @Transactional
    public CodeUpdateResponse createUpdate(Long projectId, CreateUpdateRequest request) {
        CodeUpdate codeUpdate = CodeUpdate.builder()
                .projectId(projectId)
                .developerId(request.getDeveloperId())
                .title(request.getTitle())
                .description(request.getDescription())
                .versionType(request.getVersionType())
                .oldStatus(null)
                .newStatus(request.getNewStatus())
                .build();

        CodeUpdate saved = codeUpdateRepository.save(codeUpdate);

        saveHistory(saved);

        return codeUpdateMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CodeUpdateResponse getUpdateById(Long id) {
        CodeUpdate codeUpdate = findById(id);
        return codeUpdateMapper.toResponse(codeUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodeUpdateResponse> getAllUpdates() {
        return codeUpdateRepository.findAll()
                .stream()
                .map(codeUpdateMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CodeUpdateResponse editUpdate(Long id, EditUpdateRequest request) {
        CodeUpdate existing = findById(id);

        if (request.getTitle() != null) existing.setTitle(request.getTitle());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getVersionType() != null) existing.setVersionType(request.getVersionType());

        if (request.getNewStatus() != null && !request.getNewStatus().equals(existing.getNewStatus())) {
            existing.setOldStatus(existing.getNewStatus());
            existing.setNewStatus(request.getNewStatus());
            saveHistory(existing);
        }

        CodeUpdate updated = codeUpdateRepository.save(existing);
        return codeUpdateMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteUpdate(Long id) {
        CodeUpdate codeUpdate = findById(id);
        codeUpdateRepository.delete(codeUpdate);
    }

    @Override
    @Transactional
    public void deleteUpdateByProject(Long projectId, Long updateId) {
        CodeUpdate codeUpdate = codeUpdateRepository.findByIdAndProjectId(updateId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Update not found with id: " + updateId + " for project: " + projectId));
        codeUpdateRepository.delete(codeUpdate);
    }

    @Transactional
public void linkTask(Long updateId, Long taskId) {
    CodeUpdate codeUpdate = findById(updateId);
    UpdateTask task = UpdateTask.builder()
            .codeUpdate(codeUpdate)
            .taskId(taskId)
            .build();
    UpdateTaskRepository.save(task);
}

@Transactional
public void unlinkTask(Long updateId, Long taskId) {
    UpdateTask task = UpdateTaskRepository
            .findByCodeUpdateIdAndTaskId(updateId, taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task link not found"));
    UpdateTaskRepository.delete(task);
}

@Transactional(readOnly = true)
public List<Long> getTasksByUpdateId(Long updateId) {
    return UpdateTaskRepository.findAllByCodeUpdateId(updateId)
            .stream()
            .map(UpdateTask::getTaskId)
            .collect(Collectors.toList());
}
    // ---- Private Helpers ----

    private CodeUpdate findById(Long id) {
        return codeUpdateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CodeUpdate not found with id: " + id));
    }

    private void saveHistory(CodeUpdate codeUpdate) {
        UpdateHistory history = UpdateHistory.builder()
                .codeUpdate(codeUpdate)
                .changedAt(LocalDateTime.now())
                .build();
        updateHistoryRepository.save(history);
    }
}