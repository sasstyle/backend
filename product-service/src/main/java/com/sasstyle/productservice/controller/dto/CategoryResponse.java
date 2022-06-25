package com.sasstyle.productservice.controller.dto;

import com.sasstyle.productservice.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryResponse {

    @Schema(description = "카테고리 아이디", example = "1", required = true)
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "의류", required = true)
    private String name;

    @Schema(description = "카테고리 깊이", example = "0", required = true)
    private int depth;

    @Schema(description = "자식 카테고리")
    private List<CategoryResponse> children;

    public CategoryResponse(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.depth = category.getDepth();
        this.children = category.getChildren().stream().map(CategoryResponse::new).collect(Collectors.toList());
    }
}
