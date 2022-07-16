package com.sasstyle.productservice;

import com.sasstyle.productservice.entity.Category;

public class CategoryDummy {

    public static Category 의류() {
        return Category.builder()
                .id(1L)
                .name("의류")
                .build();
    }

    public static Category 상의() {
        return Category.builder()
                .id(2L)
                .category(의류())
                .name("상의")
                .build();
    }
}
