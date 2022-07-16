package com.sasstyle.productservice;

import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductProfile;

import java.util.ArrayList;

import static com.sasstyle.productservice.UserDummy.USER_ID;

public class ProductDummy {

    private static final String PROFILE_URL = "https://picsum.photos/seed/picsum/200/300";
    private static final String PRODUCT_NAME = "한정판_맨투맨";
    private static final int PRODUCT_PRICE = 10000;
    private static final int PRODUCT_STOCKQUANTITY = 10;


    public static ProductRequest 상품_요청() {
        return new ProductRequest(
                1L,
                PROFILE_URL,
                PRODUCT_NAME,
                PRODUCT_PRICE,
                PRODUCT_STOCKQUANTITY,
                new ArrayList<>()
        );
    }

    public static ProductProfile 프로필() {
        return ProductProfile.builder()
                .profileUrl(PROFILE_URL)
                .build();
    }

    public static Product 한정판_맨투맨() {
        return Product.builder()
                .id(1L)
                .userId(USER_ID)
                .category(CategoryDummy.의류())
                .productProfile(프로필())
                .productImages(new ArrayList<>())
                .price(PRODUCT_PRICE)
                .stockQuantity(PRODUCT_STOCKQUANTITY)
                .build();
    }
}
