package com.sasstyle.orderservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sasstyle.orderservice.entity.OrderStatus.CANCEL;
import static com.sasstyle.orderservice.entity.OrderStatus.ORDER;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String userId;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private OrderStatus status;

    @CreatedDate
    private LocalDateTime orderDate;

    @Builder
    public Order(Long id, String userId, OrderStatus status, String address) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.address = new Address(address);
    }

    //== 비지니스 메서드 ==//
    public void order() {
        this.status = ORDER;
    }

    public void cancel() {
        this.status = CANCEL;
    }
}
