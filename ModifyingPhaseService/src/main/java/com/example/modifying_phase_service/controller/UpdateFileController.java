package com.example.modifying_phase_service.controller;



import com.example.modifying_phase_service.dto.request.UploadFileRequest;
import com.example.modifying_phase_service.dto.response.ApiResponse;
import com.example.modifying_phase_service.dto.response.UpdateFileResponse;
import com.example.modifying_phase_service.service.UpdateFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class UpdateFileController {

    private final UpdateFileService updateFileService;

    @PostMapping("/upload-file")
    public ResponseEntity<ApiResponse<UpdateFileResponse>> uploadFile(
            @Valid @RequestBody UploadFileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        updateFileService.uploadFile(request), "File uploaded successfully"));
    }

    @DeleteMapping("/delete-file/{fileId}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable Long fileId) {
        updateFileService.deleteFile(fileId);
        return ResponseEntity.ok(ApiResponse.success(null, "File deleted successfully"));
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<ApiResponse<List<UpdateFileResponse>>> getFilesByUpdateId(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                updateFileService.getFilesByUpdateId(id), "Files fetched successfully"));
    }
}