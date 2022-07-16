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
public class ProductWish extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "product_wish_id")
    private Long id;

    private String userId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
