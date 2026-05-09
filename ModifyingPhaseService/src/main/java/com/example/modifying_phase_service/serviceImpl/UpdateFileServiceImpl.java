// serviceImpl/UpdateFileServiceImpl.java
package com.example.modifying_phase_service.serviceImpl;

import com.example.modifying_phase_service.dto.request.UploadFileRequest;
import com.example.modifying_phase_service.dto.response.UpdateFileResponse;
import com.example.modifying_phase_service.entity.CodeUpdate;
import com.example.modifying_phase_service.entity.UpdateFile;
import com.example.modifying_phase_service.exception.ResourceNotFoundException;
import com.example.modifying_phase_service.mapper.UpdateFileMapper;
import com.example.modifying_phase_service.repository.CodeUpdateRepository;
import com.example.modifying_phase_service.repository.UpdateFileRepository;
import com.example.modifying_phase_service.service.UpdateFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateFileServiceImpl implements UpdateFileService {

    private final UpdateFileRepository updateFileRepository;
    private final CodeUpdateRepository codeUpdateRepository;
    private final UpdateFileMapper updateFileMapper;

    @Override
    @Transactional
    public UpdateFileResponse uploadFile(UploadFileRequest request) {
        CodeUpdate codeUpdate = codeUpdateRepository.findById(request.getUpdateId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CodeUpdate not found with id: " + request.getUpdateId()));

        UpdateFile file = UpdateFile.builder()
                .codeUpdate(codeUpdate)
                .fileName(request.getFileName())
                .fileUrl(request.getFileUrl())
                .fileType(request.getFileType())
                .build();

        return updateFileMapper.toResponse(updateFileRepository.save(file));
    }

    @Override
    @Transactional
    public void deleteFile(Long fileId) {
        UpdateFile file = updateFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + fileId));
        updateFileRepository.delete(file);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UpdateFileResponse> getFilesByUpdateId(Long updateId) {
        return updateFileRepository.findAllByCodeUpdateId(updateId)
                .stream()
                .map(updateFileMapper::toResponse)
                .collect(Collectors.toList());
    }
}