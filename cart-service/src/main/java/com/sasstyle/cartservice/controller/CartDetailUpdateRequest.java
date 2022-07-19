package com.sasstyle.cartservice.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDetailUpdateRequest {

    private Long productId;
    private int count;
}
