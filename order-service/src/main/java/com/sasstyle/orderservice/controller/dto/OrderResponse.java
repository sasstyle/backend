package com.sasstyle.orderservice.controller.dto;

import com.sasstyle.orderservice.entity.Order;
import com.sasstyle.orderservice.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class OrderResponse {

    @Schema(description = "총 주문 가격", example = "10000", required = true)
    private int totalPrice;

    @Schema(description = "주문 상품 목록", required = true)
    private List<OrderProductResponse> orderProducts;

    @Schema(description = "주문 상태", example = "ORDER", required = true)
    private OrderStatus status;

    @Schema(description = "주문 날짜", example = "", required = true)
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
