package com.sasstyle.productservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private Long categoryId;
    private String imageUrl;
    private String name;
    private int price;
    private int stockQuantity;

    private String topDescription;
    private String bottomDescription;
    private List<String> detailImages;
}
