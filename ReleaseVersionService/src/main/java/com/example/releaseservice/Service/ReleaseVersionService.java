package com.se2project.releaseservice.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReleaseVersionService {

    private final ReleaseVersionRepository repository;

    public ReleaseVersionService(ReleaseVersionRepository repository) {
        this.repository = repository;
    }

    // Create release version
    public ReleaseVersion create(
            String versionName,
            String description,
            String createdBy
    ) {

        ReleaseVersion version = new ReleaseVersion();

        version.setVersionName(versionName);
        version.setDescription(description);
        version.setCreatedBy(createdBy);
        version.setCreatedAt(LocalDateTime.now());
        version.setReleased(false);

        return repository.save(version);
    }

    // Get all versions
    public List<ReleaseVersion> getAll() {
        return repository.findAll();
    }

    // Get by id
    public ReleaseVersion get(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Release version not found"));
    }

    // Release version
    public ReleaseVersion release(Long id) {

        ReleaseVersion version = get(id);

        if (version.isReleased()) {
            throw new BadRequestException("Version already released");
        }

        version.setReleased(true);

        return repository.save(version);
    }

    // Delete version
    public void delete(Long id) {

        ReleaseVersion version = get(id);

        repository.delete(version);
    }
}