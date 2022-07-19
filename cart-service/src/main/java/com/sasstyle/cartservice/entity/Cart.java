package com.sasstyle.cartservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Cart extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    private String userId;

    @OneToMany(mappedBy = "cart", cascade = ALL)
    @Builder.Default
    private List<CartDetail> cartDetails = new ArrayList<>();

    public void addCartDetail(CartDetail cartDetail) {
        this.cartDetails.add(cartDetail);
        cartDetail.setCart(this);
    }
}
