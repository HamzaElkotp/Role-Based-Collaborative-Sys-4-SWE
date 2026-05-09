package com.example.modifying_phase_service.service;



import com.example.modifying_phase_service.dto.request.CreateUpdateRequest;
import com.example.modifying_phase_service.dto.request.EditUpdateRequest;
import com.example.modifying_phase_service.dto.response.CodeUpdateResponse;

import java.util.List;

public interface CodeUpdateService {
    CodeUpdateResponse createUpdate(Long projectId, CreateUpdateRequest request);
    CodeUpdateResponse getUpdateById(Long id);
    List<CodeUpdateResponse> getAllUpdates();
    CodeUpdateResponse editUpdate(Long id, EditUpdateRequest request);
    void deleteUpdate(Long id);
    void deleteUpdateByProject(Long projectId, Long updateId);
    void linkTask(Long updateId, Long taskId);
    void unlinkTask(Long updateId, Long taskId);
    List<Long> getTasksByUpdateId(Long updateId);
}