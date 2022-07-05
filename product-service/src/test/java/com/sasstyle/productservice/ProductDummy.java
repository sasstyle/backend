package com.sasstyle.productservice;

import com.sasstyle.productservice.entity.Category;
import com.sasstyle.productservice.entity.Product;
import com.sasstyle.productservice.entity.ProductProfile;

import java.util.ArrayList;

public class ProductDummy {

    public static final String USER_ID = "71fb5346-dcca-4a9d-ab00-467962aac9c4";
    public static final String BRAND_NAME = "싸스타일";

    public static Product dummy(Long id, Category category, ProductProfile productProfile, String name,
                                int price, int stockQuantity, String topDescription, String bottomDescription) {
        return Product.builder()
                .id(id)
                .category(category)
                .userId(USER_ID)
                .brandName(BRAND_NAME)
                .productProfile(productProfile)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .topDescription(topDescription)
                .bottomDescription(bottomDescription)
                .productImages(new ArrayList<>())
                .build();
    }
}
