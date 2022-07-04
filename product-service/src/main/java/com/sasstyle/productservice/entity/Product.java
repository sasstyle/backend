package com.sasstyle.productservice.entity;

import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
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
    private String imageUrl;
    private String name;
    private int price;
    private int stockQuantity;
    private String topDescription;
    private String bottomDescription;
    private long views;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductDetail> productDetails = new ArrayList<>();

    //== 비지니스 메서드 ==//
    public static Product create(Category category, String userId, String brandName, ProductRequest request) {
        return Product.builder()
                .category(category)
                .userId(userId)
                .brandName(brandName)
                .imageUrl(request.getImageUrl())
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .topDescription(request.getTopDescription())
                .bottomDescription(request.getBottomDescription())
                .views(0L)
                .productDetails(new ArrayList<>())
                .build();
    }

    public void update(ProductUpdateRequest request) {
        this.imageUrl = request.getImageUrl();
        this.name = request.getName();
        this.price = request.getPrice();
        this.stockQuantity = request.getStockQuantity();
        this.topDescription = request.getTopDescription();
        this.bottomDescription = request.getBottomDescription();
    }

    public void addDetailImage(ProductDetail productDetail) {
        this.productDetails.add(productDetail);
        productDetail.setProduct(this);
    }
}
