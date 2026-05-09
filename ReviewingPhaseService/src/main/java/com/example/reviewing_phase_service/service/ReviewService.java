package com.example.reviewing_phase_service.service;

import com.example.reviewing_phase_service.dto.ReviewRequest;
import com.example.reviewing_phase_service.dto.ReviewResponse;
import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request);
    ReviewResponse getReviewById(Long id);
    List<ReviewResponse> getAllReviews();
    List<ReviewResponse> getReviewsByProject(Long projectId);
    ReviewResponse updateReview(Long id, ReviewRequest request);
    void deleteReview(Long id);
    ReviewResponse approveProject(Long id, String comment);
    ReviewResponse rejectProject(Long id, String comment);
}