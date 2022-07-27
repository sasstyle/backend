package com.sasstyle.reviewservice.repository;

import com.sasstyle.reviewservice.controller.dto.ReviewResponse;
import com.sasstyle.reviewservice.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewQueryRepository {

    Page<ReviewResponse> findAllByProductId(Long productId, Pageable pageable);
    Optional<Review> findByIdWithReviewImage(Long reviewId);
}
