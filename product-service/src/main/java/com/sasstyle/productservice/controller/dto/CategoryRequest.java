package com.sasstyle.productservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @Schema(description = "카테고리 아이디", example = "1")
    private Long categoryId;

    @Schema(description = "이름", example = "의류", required = true)
    private String name;
}
