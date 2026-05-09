package com.se2project.piplineservice.controller;

import com.se2project.piplineservice.Service.PipelineService;
import com.se2project.piplineservice.entity.Update;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/updates")
public class PipelineController {

    private final PipelineService service;

    public PipelineController(PipelineService service) {
        this.service = service;
    }

    // Create update
    @PostMapping
    public Update create(@RequestBody Update req) {
        return service.create(req.getTitle(), req.getCreatedBy());
    }

    // Get by id
    @GetMapping("/{id}")
    public Update get(@PathVariable Long id) {
        return service.get(id);
    }

    // Get all
    @GetMapping
    public List<Update> getAll() {
        return service.getAll();
    }

    // Move to testing
    @PostMapping("/{id}/testing")
    public Update moveToTesting(@PathVariable Long id) {
        return service.moveToTesting(id);
    }

    // Move to review
    @PostMapping("/{id}/review")
    public Update moveToReview(@PathVariable Long id) {
        return service.moveToReview(id);
    }

    // Back to modifying
    @PostMapping("/{id}/modifying")
    public Update moveToModifying(@PathVariable Long id) {
        return service.moveToModifying(id);
    }

    // Merge
    @PostMapping("/{id}/merge")
    public Update merge(@PathVariable Long id) {
        return service.merge(id);
    }

    // Reject
    @PostMapping("/{id}/reject")
    public Update reject(@PathVariable Long id) {
        return service.reject(id);
    }

    // Set test result
    @PostMapping("/{id}/test-result")
    public Update setTest(
            @PathVariable Long id,
            @RequestParam boolean passed
    ) {
        return service.setTestResult(id, passed);
    }
}
