package com.example.reviewing_phase_service.service;

import com.example.reviewing_phase_service.dto.ReviewRequest;
import com.example.reviewing_phase_service.dto.ReviewResponse;
import com.example.reviewing_phase_service.enums.ReviewStatus;
import com.example.reviewing_phase_service.exception.ReviewNotFoundException;
import com.example.reviewing_phase_service.model.Review;
import com.example.reviewing_phase_service.model.ReviewHistory;
import com.example.reviewing_phase_service.repository.ReviewHistoryRepository;
import com.example.reviewing_phase_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewHistoryRepository;

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        Review review = Review.builder()
                .projectId(request.getProjectId())
                .reviewerId(request.getReviewerId())
                .comment(request.getComment())
                .score(request.getScore())
                .build();

        return mapToResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        return mapToResponse(findReviewOrThrow(id));
    }

    @Override
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getReviewsByProject(Long projectId) {
        return reviewRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review review = findReviewOrThrow(id);

        review.setComment(request.getComment());
        review.setScore(request.getScore());
        if (request.getStatus() != null) {
            saveHistory(review, request.getStatus());
            review.setStatus(request.getStatus());
        }

        return mapToResponse(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.delete(findReviewOrThrow(id));
    }

    @Override
    public ReviewResponse approveProject(Long id, String comment) {
        return changeStatus(id, ReviewStatus.APPROVED, comment);
    }

    @Override
    public ReviewResponse rejectProject(Long id, String comment) {
        return changeStatus(id, ReviewStatus.REJECTED, comment);
    }

    // ── private helpers ─────────────────────────────────────────

    private ReviewResponse changeStatus(Long id, ReviewStatus newStatus, String comment) {
        Review review = findReviewOrThrow(id);
        saveHistory(review, newStatus);
        review.setStatus(newStatus);
        review.setComment(comment);
        return mapToResponse(reviewRepository.save(review));
    }

    private void saveHistory(Review review, ReviewStatus newStatus) {
        ReviewHistory history = ReviewHistory.builder()
                .review(review)
                .oldReviewId(review.getId())
                .newStatus(newStatus)
                .build();
        reviewHistoryRepository.save(history);
    }

    private Review findReviewOrThrow(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    private ReviewResponse mapToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .projectId(review.getProjectId())
                .reviewerId(review.getReviewerId())
                .status(review.getStatus())
                .comment(review.getComment())
                .score(review.getScore())
                .createdAt(review.getCreatedAt())
                .build();
    }
}