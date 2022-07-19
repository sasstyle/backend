package com.sasstyle.cartservice;

import com.sasstyle.cartservice.entity.CartDetail;

import static com.sasstyle.cartservice.ProductDummy.PRODUCT_ID;

public class CartDetailDummy {
    public static final Long CART_DETAIL_ID = 2L;
    public static final int COUNT = 1;
    public static final int ADD_COUNT = 2;
    public static final int UPDATE_COUNT = 5;

    public static CartDetail dummy() {
        return CartDetail.builder()
                .id(CART_DETAIL_ID)
                .productId(PRODUCT_ID)
                .count(COUNT)
                .cart(CartDummy.dummy())
                .build();
    }

    public static CartDetail updateDummy() {
        return CartDetail.builder()
                .id(CART_DETAIL_ID)
                .productId(PRODUCT_ID)
                .count(UPDATE_COUNT)
                .cart(CartDummy.dummy())
                .build();
    }

}
