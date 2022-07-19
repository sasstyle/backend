package com.sasstyle.cartservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartDetailRequest {

    @Schema(description = "상품 아이디", example = "1", required = true)
    private Long productId;

    @Schema(description = "주문 수량", example = "1", required = true)
    private int count;
}
