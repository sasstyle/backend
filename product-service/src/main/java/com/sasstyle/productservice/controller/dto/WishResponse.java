package com.sasstyle.productservice.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class WishResponse {

    private Long productId;
    private String profileUrl;
    private String productName;
    private String brandName;
    private int price;

    @QueryProjection
    public WishResponse(Long productId, String profileUrl, String productName, String brandName, int price) {
        this.productId = productId;
        this.profileUrl = profileUrl;
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
    }
}
