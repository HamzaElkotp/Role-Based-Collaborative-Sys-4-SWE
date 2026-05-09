package com.se2project.releaseservice.controller;
 import com.se2project.releaseservice.entity.ReleaseVersion;
import com.se2project.releaseservice.Service.ReleaseVersionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/release-versions")
public class ReleaseVersionController {

    private final ReleaseVersionService service;

    public ReleaseVersionController(ReleaseVersionService service) {
        this.service = service;
    }

    // Create release version
    @PostMapping
    public ReleaseVersion create(@RequestBody ReleaseVersion req) {

        return service.create(
                req.getVersionName(),
                req.getDescription(),
                req.getCreatedBy()
        );
    }

    // Get all
    @GetMapping
    public List<ReleaseVersion> getAll() {
        return service.getAll();
    }

    // Get by id
    @GetMapping("/{id}")
    public ReleaseVersion get(@PathVariable Long id) {
        return service.get(id);
    }

    // Release version
    @PostMapping("/{id}/release")
    public ReleaseVersion release(@PathVariable Long id) {
        return service.release(id);
    }

    // Delete version
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}