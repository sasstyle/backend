package com.sasstyle.cartservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartRequest {

    private Long productId;
    private int count;
}
