package com.sasstyle.reviewservice.controller;

import com.sasstyle.reviewservice.controller.dto.ReviewDetailResponse;
import com.sasstyle.reviewservice.controller.dto.ReviewFindRequest;
import com.sasstyle.reviewservice.controller.dto.ReviewRequest;
import com.sasstyle.reviewservice.controller.dto.ReviewResponse;
import com.sasstyle.reviewservice.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "리뷰 조회", description = "모든 리뷰를 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> reviews(@RequestBody ReviewFindRequest request, Pageable pageable) {
        return ResponseEntity
                .ok(reviewService.findAllByProductId(request.getProductId(), pageable));
    }

    @Operation(summary = "리뷰 상세 조회", description = "리뷰 아이디에 해당하는 리뷰 상세를 조회합니다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> review(@PathVariable Long reviewId) {
        return ResponseEntity
                .ok(new ReviewDetailResponse(reviewService.findByIdWithReviewImage(reviewId)));
    }

    @Operation(summary = "리뷰 작성", description = "새로운 리뷰를 작성합니다.")
    @PostMapping
    public void createReview(@RequestHeader String userId, @RequestBody ReviewRequest request) {
        reviewService.createReview(userId, request);
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 아이디에 해당하는 리뷰를 삭제합니다.")
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@RequestHeader String userId, @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }
}
