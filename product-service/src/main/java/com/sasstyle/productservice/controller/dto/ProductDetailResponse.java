package com.sasstyle.productservice.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductDetailResponse {

    @Schema(description = "상품 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String profileUrl;

    @Schema(description = "상품 이름", example = "한정판 후드티", required = true)
    private String name;

    @Schema(description = "브랜드 이름", example = "싸스타일", required = true)
    private String brandName;

    @Schema(description = "상품 가격", example = "10000", required = true)
    private int price;

    @Schema(description = "상품 상세 이미지", example = "[https://picsum.photos/seed/picsum/200/300]")
    private List<String> images;

    @Schema(description = "상품 좋아요", example = "true", required = true)
    private boolean isWish;

    @QueryProjection
    public ProductDetailResponse(String profileUrl, String name, String brandName, int price, List<ProductImage> images, boolean isWish) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.brandName = brandName;
        this.price = price;
        this.images = images
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
        this.isWish = isWish;
    }
}
