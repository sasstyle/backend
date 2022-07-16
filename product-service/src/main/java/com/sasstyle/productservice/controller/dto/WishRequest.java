package com.sasstyle.productservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WishRequest {

    @Schema(description = "상품 아이디", example = "1", required = true)
    private Long productId;
}
