package com.sasstyle.productservice.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductAutoCompleteResponse {

    @Schema(description = "상품 아이디", example = "1", required = true)
    private Long productId;

    @Schema(description = "상품 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String imageUrl;

    @Schema(description = "상품 이름", example = "한정판 후드티", required = true)
    private String name;

    @QueryProjection
    public ProductAutoCompleteResponse(Long productId, String imageUrl, String name) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
