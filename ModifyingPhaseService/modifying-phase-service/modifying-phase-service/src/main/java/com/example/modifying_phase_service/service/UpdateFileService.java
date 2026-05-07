package com.example.modifying_phase_service.service;



import com.example.modifying_phase_service.dto.request.UploadFileRequest;
import com.example.modifying_phase_service.dto.response.UpdateFileResponse;

import java.util.List;

public interface UpdateFileService {
    UpdateFileResponse uploadFile(UploadFileRequest request);
    void deleteFile(Long fileId);
    List<UpdateFileResponse> getFilesByUpdateId(Long updateId);
}