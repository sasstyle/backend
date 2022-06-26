package com.sasstyle.productservice.controller.dto;

import com.sasstyle.productservice.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class ProductDetailResponse {

    @Schema(description = "상품 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String imageUrl;

    @Schema(description = "상품 이름", example = "한정판 후드티", required = true)
    private String name;

    @Schema(description = "상품 가격", example = "10000", required = true)
    private int price;

    @Schema(description = "상단 설명", example = "많은 구매 부탁드립니다.")
    private String topDescription;

    @Schema(description = "하단 설명", example = "한정판매!")
    private String bottomDescription;

    @Schema(description = "상품 상세 이미지", example = "[https://picsum.photos/seed/picsum/200/300]")
    private List<String> detailImages;

    public ProductDetailResponse(Product product) {
        this.imageUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = product.getPrice();
        this.topDescription = product.getTopDescription();
        this.bottomDescription = product.getBottomDescription();
        this.detailImages = product.getProductDetails().stream()
                .map(detail -> String.valueOf(detail.getImageUrl()))
                .collect(Collectors.toList());
    }
}
