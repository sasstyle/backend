package com.sasstyle.reviewservice.service;

import com.sasstyle.reviewservice.controller.dto.ReviewRequest;
import com.sasstyle.reviewservice.controller.dto.ReviewResponse;
import com.sasstyle.reviewservice.entity.Review;
import com.sasstyle.reviewservice.entity.ReviewImage;
import com.sasstyle.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Page<ReviewResponse> findAllByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findAllByProductId(productId, pageable);
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾지 못했습니다."));
    }

    public Review findByIdWithReviewImage(Long reviewId) {
        return reviewRepository.findByIdWithReviewImage(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾지 못했습니다."));
    }

    @Transactional
    public void createReview(String userId, ReviewRequest request) {
        Review review = Review.builder()
                .userId(userId)
                .content(request.getContent())
                .productId(request.getProductId())
                .rate(request.getRate())
                .build();

        if (isNotEmptyReviewImage(request)) {
            List<ReviewImage> reviewImages = request.getImages().stream()
                    .map(ReviewImage::new)
                    .collect(Collectors.toList());

            reviewImages.stream()
                    .forEach(reviewImage -> review.addReviewImage(reviewImage));
        }

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(String userId, Long reviewId) {
        Review review = findById(reviewId);

        if (isNotReviewer(review.getUserId(), userId)) {
            throw new IllegalArgumentException("리뷰어만 리뷰를 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }

    private boolean isNotReviewer(String reviewerUserId, String userId) {
        return !reviewerUserId.equals(userId);
    }

    private boolean isNotEmptyReviewImage(ReviewRequest request) {
        return request.getImages() != null && !request.getImages().isEmpty();
    }
}
