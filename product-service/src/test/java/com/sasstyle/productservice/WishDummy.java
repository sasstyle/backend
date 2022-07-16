package com.sasstyle.productservice;

import com.sasstyle.productservice.entity.ProductWish;

import static com.sasstyle.productservice.UserDummy.USER_ID;

public class WishDummy {

    public static ProductWish 더미() {
        return ProductWish
                .builder()
                .id(1L)
                .userId(USER_ID)
                .product(ProductDummy.한정판_맨투맨())
                .build();
    }
}
