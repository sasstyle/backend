package com.sasstyle.reviewservice.controller.dto;

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
public class ReviewRequest {

    @Schema(description = "상품 아이디", example = "1", required = true)
    private Long productId;

    @Schema(description = "리뷰 후기", example = "너무 상품이 마음에 들어요~", required = true)
    private String content;

    @Schema(description = "리뷰 상세 이미지", example = "[https://picsum.photos/seed/picsum/200/300]")
    private List<String> images;

    @Schema(description = "리뷰 평점", example = "5", required = true)
    private int rate;
}
