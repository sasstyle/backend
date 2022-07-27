package com.sasstyle.reviewservice.controller.dto;

import com.sasstyle.reviewservice.entity.Review;
import com.sasstyle.reviewservice.entity.ReviewImage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewDetailResponse {

    private String content;
    private List<String> images;
    private int rate;

    public ReviewDetailResponse(Review review) {
        this.content = review.getContent();
        this.images = review.getReviewImages().stream()
                .map(ReviewImage::getImageUrl)
                .collect(Collectors.toList());
        this.rate = review.getRate();
    }
}
