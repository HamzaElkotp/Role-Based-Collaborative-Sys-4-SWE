package com.example.modifying_phase_service.controller;



import com.example.modifying_phase_service.dto.request.CreateUpdateRequest;
import com.example.modifying_phase_service.dto.request.EditUpdateRequest;
import com.example.modifying_phase_service.dto.response.ApiResponse;
import com.example.modifying_phase_service.dto.response.CodeUpdateResponse;
import com.example.modifying_phase_service.service.CodeUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class CodeUpdateController {

    private final CodeUpdateService codeUpdateService;

    @PostMapping("/{projectId}/create-update")
    public ResponseEntity<ApiResponse<CodeUpdateResponse>> createUpdate(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateUpdateRequest request) {
        CodeUpdateResponse response = codeUpdateService.createUpdate(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Update created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CodeUpdateResponse>> getUpdateById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                codeUpdateService.getUpdateById(id), "Update fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CodeUpdateResponse>>> getAllUpdates() {
        return ResponseEntity.ok(ApiResponse.success(
                codeUpdateService.getAllUpdates(), "Updates fetched successfully"));
    }

    @PutMapping("/{id}/edit-update")
    public ResponseEntity<ApiResponse<CodeUpdateResponse>> editUpdate(
            @PathVariable Long id,
            @Valid @RequestBody EditUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                codeUpdateService.editUpdate(id, request), "Update edited successfully"));
    }

    @DeleteMapping("/delete-update/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUpdate(@PathVariable Long id) {
        codeUpdateService.deleteUpdate(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Update deleted successfully"));
    }

    @DeleteMapping("/{projectId}/update/{updateId}")
    public ResponseEntity<ApiResponse<Void>> deleteUpdateByProject(
            @PathVariable Long projectId,
            @PathVariable Long updateId) {
        codeUpdateService.deleteUpdateByProject(projectId, updateId);
        return ResponseEntity.ok(ApiResponse.success(null, "Update deleted successfully"));
    }

    @PostMapping("/{updateId}/link-task/{taskId}")
public ResponseEntity<ApiResponse<Void>> linkTask(
        @PathVariable Long updateId,
        @PathVariable Long taskId) {
    codeUpdateService.linkTask(updateId, taskId);
    return ResponseEntity.ok(ApiResponse.success(null, "Task linked successfully"));
}

@DeleteMapping("/{updateId}/unlink-task/{taskId}")
public ResponseEntity<ApiResponse<Void>> unlinkTask(
        @PathVariable Long updateId,
        @PathVariable Long taskId) {
    codeUpdateService.unlinkTask(updateId, taskId);
    return ResponseEntity.ok(ApiResponse.success(null, "Task unlinked successfully"));
}

@GetMapping("/{updateId}/tasks")
public ResponseEntity<ApiResponse<List<Long>>> getTasksByUpdateId(
        @PathVariable Long updateId) {
    return ResponseEntity.ok(ApiResponse.success(
            codeUpdateService.getTasksByUpdateId(updateId), "Tasks fetched successfully"));
}
}