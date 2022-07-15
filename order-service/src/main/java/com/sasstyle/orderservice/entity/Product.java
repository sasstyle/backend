package com.sasstyle.orderservice.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Product {

    private Long productId;
    private String profileUrl;
    private String productName;

    private int orderPrice;
    private int count;

    @Builder
    public Product(Long productId, String profileUrl, String productName, int orderPrice, int count) {
        this.productId = productId;
        this.profileUrl = profileUrl;
        this.productName = productName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
