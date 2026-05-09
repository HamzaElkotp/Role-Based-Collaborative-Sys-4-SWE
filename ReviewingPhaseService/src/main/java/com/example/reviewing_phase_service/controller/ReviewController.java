package com.example.reviewing_phase_service.controller;

import com.example.reviewing_phase_service.dto.ReviewRequest;
import com.example.reviewing_phase_service.dto.ReviewResponse;
import com.example.reviewing_phase_service.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // ── CRUD ────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(request));
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProject(
            @PathVariable Long projectId) {
        return ResponseEntity.ok(reviewService.getReviewsByProject(projectId));
    }

    @PutMapping("/update-review/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    @DeleteMapping("/delete-review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    // ── Project Actions ─────────────────────────────────────────

    @PostMapping("/approved-project/{id}")
    public ResponseEntity<ReviewResponse> approveProject(
            @PathVariable Long id,
            @RequestParam String comment) {
        return ResponseEntity.ok(reviewService.approveProject(id, comment));
    }

    @PostMapping("/rejected-project/{id}")
    public ResponseEntity<ReviewResponse> rejectProject(
            @PathVariable Long id,
            @RequestParam String comment) {
        return ResponseEntity.ok(reviewService.rejectProject(id, comment));
    }
}