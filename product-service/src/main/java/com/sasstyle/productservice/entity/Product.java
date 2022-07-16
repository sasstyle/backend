package com.sasstyle.productservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
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

    @OneToMany(mappedBy = "product", cascade = ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = REMOVE, orphanRemoval = true)
    private List<ProductWish> productWishes = new ArrayList<>();

    //== 비지니스 메서드 ==//
    @Builder
    public Product(Long id, Category category, String userId, String brandName, ProductProfile productProfile, String name,
                   int price, int stockQuantity, List<ProductImage> productImages) {
        this.id = id;
        this.category = category;
        this.userId = userId;
        this.brandName = brandName;
        productProfile.setProduct(this);
        this.productProfile = productProfile;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        productImages
                .stream()
                .forEach(this::addProductImage);
        this.productImages = productImages;
    }

    public void update(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.setProduct(this);
    }
}
