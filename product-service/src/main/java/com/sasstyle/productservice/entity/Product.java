package com.sasstyle.productservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Product extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String userId;
    private String brandName;

    @OneToOne(mappedBy = "product", fetch = LAZY, cascade = ALL)
    private ProductProfile productProfile;
    private String name;
    private int price;
    private int stockQuantity;
    private String topDescription;
    private String bottomDescription;
    private long views;

    @OneToMany(mappedBy = "product", cascade = ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    //== 비지니스 메서드 ==//
    @Builder
    public Product(Long id, Category category, String userId, String brandName, String profileUrl, String name,
                   int price, int stockQuantity, String topDescription, String bottomDescription, long views, List<String> images) {
        this.id = id;
        this.category = category;
        this.userId = userId;
        this.brandName = brandName;
        this.productProfile = ProductProfile.builder()
                .product(this)
                .profileUrl(profileUrl)
                .build();
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.topDescription = topDescription;
        this.bottomDescription = bottomDescription;
        this.views = views;
        this.productImages = new ArrayList<>();

        if (images != null && !images.isEmpty()) {
            List<ProductImage> productImages = images.stream()
                    .map(ProductImage::new)
                    .collect(Collectors.toList());
            productImages
                    .stream()
                    .forEach(this::addProductImage);

            this.productImages = productImages;
        }
    }

    public void update(String name, int price, int stockQuantity, String topDescription, String bottomDescription) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.topDescription = topDescription;
        this.bottomDescription = bottomDescription;
    }

    public void addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.setProduct(this);
    }
}
