package com.sasstyle.productservice.controller.dto;

import com.sasstyle.productservice.entity.Category;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryResponse {

    private Long categoryId;
    private String name;
    private int depth;
    private List<CategoryResponse> children;

    public CategoryResponse(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.depth = category.getDepth();
        this.children = category.getChildren().stream().map(CategoryResponse::new).collect(Collectors.toList());
    }
}
