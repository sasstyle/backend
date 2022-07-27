package com.sasstyle.reviewservice.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewResponse {

    @Schema(description = "리뷰 아이디", example = "1", required = true)
    private Long reviewId;

    @Schema(description = "리뷰 후기", example = "너무 상품이 마음에 들어요~", required = true)
    private String content;

    @Schema(description = "리뷰 평점", example = "5", required = true)
    private int rate;

    @QueryProjection
    public ReviewResponse(Long reviewId, String content, int rate) {
        this.reviewId = reviewId;
        this.content = content;
        this.rate = rate;
    }
}
