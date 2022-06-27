package com.sasstyle.productservice.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sasstyle.productservice.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductResponse {

    @Schema(description = "카테고리 아이디", example = "1", required = true)
    private Long categoryId;

    @Schema(description = "상품 아이디", example = "1", required = true)
    private Long productId;

    @Schema(description = "상품 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String imageUrl;

    @Schema(description = "상품 이름", example = "한정판 후드티", required = true)
    private String name;

    @Schema(description = "상품 가격", example = "10000", required = true)
    private int price;

    @QueryProjection
    public ProductResponse(Long categoryId, Long productId, String imageUrl, String name, int price) {
        this.categoryId = categoryId;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public ProductResponse(Product product) {
        this.categoryId = product.getCategory().getId();
        this.productId = product.getId();
        this.imageUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
