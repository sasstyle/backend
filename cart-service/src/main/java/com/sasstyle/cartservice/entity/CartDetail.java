package com.sasstyle.cartservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class CartDetail extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "cart_detail_id")
    private Long id;

    private Long productId;

    private int count;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Builder
    public CartDetail(Long id, Long productId, int count, Cart cart) {
        this.id = id;
        this.productId = productId;
        setCount(count);
        this.cart = cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("주문 수량이 0 이하일 수 없습니다.");
        }

        this.count = count;
    }
}
