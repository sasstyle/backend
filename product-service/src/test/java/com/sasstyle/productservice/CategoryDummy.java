package com.sasstyle.productservice;

import com.sasstyle.productservice.entity.Category;

public class CategoryDummy {

    public static Category dummy(Long id, Category category, String name) {
        return Category.builder()
                .id(id)
                .category(category)
                .name(name)
                .build();
    }
}
