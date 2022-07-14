package com.sasstyle.orderservice.controller.dto;

import com.sasstyle.orderservice.entity.Order;
import com.sasstyle.orderservice.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class OrderResponse {

    private int totalPrice;
    private List<OrderProductResponse> orderProducts;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public OrderResponse(Order order) {
        this.totalPrice = order.getOrderDetails().stream()
                .mapToInt(orderProduct -> orderProduct.getProduct().getOrderPrice() * orderProduct.getProduct().getCount())
                .sum();
        this.orderProducts = order.getOrderDetails().stream()
                .map(OrderProductResponse::new)
                .collect(toList());
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }
}
