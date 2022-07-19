package com.sasstyle.cartservice;

import com.sasstyle.cartservice.entity.Cart;

import static com.sasstyle.cartservice.UserDummy.USER_ID;

public class CartDummy {

    public static final Long CART_ID = 1L;

    public static Cart dummy() {
        return Cart.builder()
                .id(CART_ID)
                .userId(USER_ID)
                .build();
    }
}
