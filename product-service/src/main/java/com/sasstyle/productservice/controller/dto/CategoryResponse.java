package com.sasstyle.productservice.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sasstyle.productservice.entity.Category;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryResponse {

    private Long id;
    private String name;
    private int depth;
    private List<CategoryResponse> children;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.depth = category.getDepth();
        this.children = category.getChildren().stream().map(CategoryResponse::new).collect(Collectors.toList());
    }
}
