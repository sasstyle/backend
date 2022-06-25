package com.sasstyle.productservice.controller.dto;

import com.sasstyle.productservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private String imageUrl;
    private String name;
    private int price;

    public ProductResponse(Product product) {
        this.imageUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
