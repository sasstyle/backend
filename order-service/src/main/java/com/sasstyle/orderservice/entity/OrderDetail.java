package com.sasstyle.orderservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Embedded
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderDetail(Long id, Long productId, String profileUrl, String productName, int orderPrice, int count) {
        this.id = id;
        this.product = Product.builder()
                .productId(productId)
                .profileUrl(profileUrl)
                .productName(productName)
                .orderPrice(orderPrice)
                .count(count)
                .build();
    }

    public void setOrder(Order order) {
        this.order = order;
        order.getOrderDetails().add(this);
    }
}
