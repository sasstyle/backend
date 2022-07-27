package com.sasstyle.reviewservice;

import com.sasstyle.reviewservice.controller.dto.ReviewRequest;
import com.sasstyle.reviewservice.controller.dto.ReviewResponse;
import com.sasstyle.reviewservice.entity.Review;

import java.util.ArrayList;
import java.util.List;

import static com.sasstyle.reviewservice.ProductDummy.PRODUCT_ID;

public class ReviewDummy {

    public static final Long REVIEW_ID = 1L;
    public static final String REVIEWER_ID = "67216e98-32ae-4f82-94b4-e7a2e6008b1f";
    public static final String CONTENT = "상품이 마음에 들어요~";

    public static ReviewRequest request() {
        return new ReviewRequest(PRODUCT_ID, CONTENT, List.of("https://picsum.photos/seed/picsum/200/300"), 5);
    }
    public static ReviewResponse response() {
        return new ReviewResponse(REVIEW_ID, CONTENT, 5);
    }

    public static Review dummy() {
        return Review.builder()
                .id(REVIEW_ID)
                .productId(PRODUCT_ID)
                .userId(REVIEWER_ID)
                .content(CONTENT)
                .rate(5)
                .reviewImages(new ArrayList<>())
                .build();
    }
}
