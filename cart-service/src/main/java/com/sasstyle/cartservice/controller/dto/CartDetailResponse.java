package com.sasstyle.cartservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartDetailResponse {

    private Long cartDetailId;
    private String profileUrl;
    private String name;
    private int price;
    private int count;
}
