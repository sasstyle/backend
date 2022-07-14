package com.sasstyle.orderservice.controller.dto;

import com.sasstyle.orderservice.entity.OrderDetail;
import lombok.Getter;

@Getter
public class OrderProductResponse {

    private String profileUrl;
    private String productName;
    private int price;
    private int count;

    public OrderProductResponse(OrderDetail orderDetail) {
        this.profileUrl = orderDetail.getProduct().getProfileUrl();
        this.productName = orderDetail.getProduct().getProductName();
        this.price = orderDetail.getProduct().getOrderPrice();
        this.count = orderDetail.getProduct().getCount();
    }
}
