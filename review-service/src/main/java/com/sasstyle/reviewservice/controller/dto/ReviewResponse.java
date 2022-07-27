package com.sasstyle.reviewservice.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private Long reviewId;
    private String content;
    private int rate;

    @QueryProjection
    public ReviewResponse(Long reviewId, String content, int rate) {
        this.reviewId = reviewId;
        this.content = content;
        this.rate = rate;
    }
}
