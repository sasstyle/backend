package com.sasstyle.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class ProductProfile extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "product_profile_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String profileUrl;

    //== 비지니스 메서드 ==//
    public void setProduct(Product product) { this.product = product; }
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
