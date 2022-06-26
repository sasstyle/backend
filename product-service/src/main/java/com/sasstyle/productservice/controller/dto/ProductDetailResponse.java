package com.sasstyle.productservice.controller.dto;

import com.sasstyle.productservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class ProductDetailResponse {

    private String imageUrl;
    private String name;
    private int price;
    private String topDescription;
    private String bottomDescription;
    private List<String> detailImages;

    public ProductDetailResponse(Product product) {
        this.imageUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = product.getPrice();
        this.topDescription = product.getTopDescription();
        this.bottomDescription = product.getBottomDescription();
        this.detailImages = product.getProductDetails().stream()
                .map(detail -> String.valueOf(detail.getImageUrl()))
                .collect(Collectors.toList());
    }
}
