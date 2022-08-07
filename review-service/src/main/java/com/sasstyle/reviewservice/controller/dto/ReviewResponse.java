package com.sasstyle.reviewservice.controller.dto;

import com.sasstyle.reviewservice.entity.Review;
import com.sasstyle.reviewservice.entity.ReviewImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewResponse {

    @Schema(description = "리뷰 아이디", example = "1", required = true)
    private Long reviewId;

    @Schema(description = "리뷰어 이름", example = "싸스타일", required = true)
    private String reviewerName;

    @Schema(description = "리뷰 후기", example = "너무 상품이 마음에 들어요~", required = true)
    private String content;

    @Schema(description = "리뷰 상세 이미지", example = "[https://picsum.photos/seed/picsum/200/300]")
    private List<String> images;

    @Schema(description = "리뷰 평점", example = "5", required = true)
    private int rate;

    public ReviewResponse(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.reviewerName = review.getReviewerName();
        this.images = review.getReviewImages().stream()
                .map(ReviewImage::getImageUrl)
                .collect(Collectors.toList());
        this.rate = review.getRate();
    }

    public ReviewResponse(Long reviewId, String content, List<String> images, int rate) {
        this.reviewId = reviewId;
        this.content = content;
        this.images = images;
        this.rate = rate;
    }
}
