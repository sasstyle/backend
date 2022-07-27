package com.sasstyle.reviewservice.controller;

import com.sasstyle.reviewservice.controller.dto.ReviewDetailResponse;
import com.sasstyle.reviewservice.controller.dto.ReviewFindRequest;
import com.sasstyle.reviewservice.controller.dto.ReviewRequest;
import com.sasstyle.reviewservice.controller.dto.ReviewResponse;
import com.sasstyle.reviewservice.entity.Review;
import com.sasstyle.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> reviews(@RequestBody ReviewFindRequest request, Pageable pageable) {
        return ResponseEntity
                .ok(reviewService.findAllByProductId(request.getProductId(), pageable));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> review(@PathVariable Long reviewId) {
        return ResponseEntity
                .ok(new ReviewDetailResponse(reviewService.findByIdWithReviewImage(reviewId)));
    }

    @PostMapping
    public void createReview(@RequestHeader String userId, @RequestBody ReviewRequest request) {
        reviewService.createReview(userId, request);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@RequestHeader String userId, @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }
}
