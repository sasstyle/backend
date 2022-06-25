package com.sasstyle.productservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    private String imageUrl;
    private String name;
    private int price;
    private int stockQuantity;
    private String topDescription;
    private String bottomDescription;
}
