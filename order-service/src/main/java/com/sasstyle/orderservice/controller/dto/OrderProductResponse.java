package com.sasstyle.orderservice.controller.dto;

import com.sasstyle.orderservice.entity.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OrderProductResponse {

    @Schema(description = "상품 이미지", example = "https://picsum.photos/seed/picsum/200/300", required = true)
    private String profileUrl;

    @Schema(description = "상품 이름", example = "한정판 원피스", required = true)
    private String productName;

    @Schema(description = "상품 가격", example = "10000", required = true)
    private int price;

    @Schema(description = "주문 수량", example = "1", required = true)
    private int count;

    public OrderProductResponse(OrderDetail orderDetail) {
        this.profileUrl = orderDetail.getProduct().getProfileUrl();
        this.productName = orderDetail.getProduct().getProductName();
        this.price = orderDetail.getProduct().getOrderPrice();
        this.count = orderDetail.getProduct().getCount();
    }
}
