package com.sasstyle.productservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @Schema(description = "카테고리 아이디", example = "1", required = true)
    private Long categoryId;

    @Schema(description = "상품 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String profileUrl;

    @Schema(description = "상품 이름", example = "한정판 후드티", required = true)
    private String name;

    @Schema(description = "상품 가격", example = "10000", required = true)
    private int price;

    @Schema(description = "재고 수량", example = "100", required = true)
    private int stockQuantity;

    @Schema(description = "상품 상세 이미지", example = "[https://picsum.photos/seed/picsum/200/300]")
    private List<String> images;
}
