package com.sasstyle.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class ProductWish extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "product_like_id")
    private Long id;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
