package com.sasstyle.cartservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartDetailResponse {

    @Schema(description = "장바구니 상품 상세 아이디", example = "2", required = true)
    private Long cartDetailId;

    @Schema(description = "상품 프로필", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String profileUrl;

    @Schema(description = "상품 이름", example = "레이어드 슬리브리스 탑_일반 기장 [화이트]", required = true)
    private String name;

    @Schema(description = "상품 가격", example = "11900", required = true)
    private int price;

    @Schema(description = "주문 수량", example = "3", required = true)
    private int count;
}
