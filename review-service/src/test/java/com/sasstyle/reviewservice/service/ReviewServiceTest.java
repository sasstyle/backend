package com.sasstyle.reviewservice.service;

import com.sasstyle.reviewservice.ReviewDummy;
import com.sasstyle.reviewservice.controller.dto.ReviewResponse;
import com.sasstyle.reviewservice.entity.Review;
import com.sasstyle.reviewservice.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.sasstyle.reviewservice.ProductDummy.PRODUCT_ID;
import static com.sasstyle.reviewservice.ReviewDummy.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @DisplayName("상품 아이디로 리뷰 조회")
    @Test
    void 상품_아이디로_리뷰_조회() {
        Page<ReviewResponse> reviews = new PageImpl<>(List.of(ReviewDummy.response()));

        given(reviewRepository.findAllByProductId(PRODUCT_ID, PageRequest.of(0, 20))).willReturn(reviews);

        Page<ReviewResponse> reviewResponses = reviewService.findAllByProductId(PRODUCT_ID, PageRequest.of(0, 20));
        assertThat(reviewResponses.getSize()).isEqualTo(1L);
        assertThat(reviewResponses.getContent().get(0).getReviewId()).isEqualTo(REVIEW_ID);
        assertThat(reviewResponses.getContent().get(0).getContent()).isEqualTo(CONTENT);
    }

    @DisplayName("리뷰 아이디로 리뷰 이미지까지 조회")
    @Test
    void 리뷰_아이디로_리뷰_이미지까지_조회() {
        given(reviewRepository.findByIdWithReviewImage(REVIEW_ID))
                .willReturn(Optional.ofNullable(dummy()));

        Review review = reviewService.findByIdWithReviewImage(REVIEW_ID);
        assertThat(review.getId()).isEqualTo(REVIEW_ID);
        assertThat(review.getUserId()).isEqualTo(REVIEWER_ID);
        assertThat(review.getContent()).isEqualTo(CONTENT);
    }

    @DisplayName("리뷰 생성")
    @Test
    void 리뷰_생성() {
        reviewService.createReview(REVIEWER_ID, request());
    }

    @DisplayName("리뷰 삭제 성공 - 리뷰를 작성자와 리뷰어랑 같은 경우")
    @Test
    void 리뷰_삭제_성공() {
        Review review = ReviewDummy.dummy();

        given(reviewRepository.findById(REVIEW_ID)).willReturn(Optional.ofNullable(review));

        reviewService.deleteReview(REVIEWER_ID, REVIEW_ID);
    }

    @DisplayName("리뷰 삭제 실패 - 리뷰를 작성자와 리뷰어랑 다른 경우")
    @Test
    void 리뷰_삭제_실패() {
        Review review = ReviewDummy.dummy();
        String DIFFERENT_REVIEWER_ID = "e980e54e-2fc0-4298-957c-1ad21140e94a";

        given(reviewRepository.findById(REVIEW_ID)).willReturn(Optional.ofNullable(review));

        Assertions.assertThatThrownBy(() ->
                reviewService.deleteReview(DIFFERENT_REVIEWER_ID, REVIEW_ID))
                .isInstanceOf(IllegalArgumentException.class);
    }
}