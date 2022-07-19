package com.sasstyle.cartservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponse {

    @Schema(description = "장바구니 아이디", example = "1")
    private Long cartId;

    @Schema(description = "장바가니 상품 정보")
    private List<CartDetailResponse> products;

    @Schema(description = "상품 총 가격", example = "55000")
    private int totalPrice;
}
