package com.sasstyle.orderservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderIdResponse {

    @Schema(description = "주문 아이디", example = "1", required = true)
    private Long orderId;
}
