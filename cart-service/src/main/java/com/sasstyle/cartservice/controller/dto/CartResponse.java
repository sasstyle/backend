package com.sasstyle.cartservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponse {

    private Long cartId;
    private List<CartDetailResponse> products;
    private int totalPrice;
}
