package com.sasstyle.orderservice.service;

import com.sasstyle.orderservice.client.ProductResponse;
import com.sasstyle.orderservice.client.ProductServiceClient;
import com.sasstyle.orderservice.controller.dto.OrderProductRequest;
import com.sasstyle.orderservice.controller.dto.OrderRequest;
import com.sasstyle.orderservice.entity.Order;
import com.sasstyle.orderservice.entity.OrderDetail;
import com.sasstyle.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sasstyle.orderservice.entity.OrderStatus.ORDER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
    }

    public List<Order> findAllByOrderer(String userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Transactional
    public Long order(String userId, OrderRequest request) {
        if (emptyData(request.getData())) {
            throw new IllegalArgumentException("주문 상품이 존재하지 않습니다.");
        }

        Order order = Order.builder()
                .userId(userId)
                .address(request.getAddress())
                .status(ORDER)
                .build();

        for (OrderProductRequest orderProductRequest : request.getData()) {
            ProductResponse productResponse = productServiceClient.findByProductId(orderProductRequest.getProductId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .productId(orderProductRequest.getProductId())
                    .profileUrl(productResponse.getProfileUrl())
                    .productName(productResponse.getName())
                    .orderPrice(productResponse.getPrice())
                    .count(orderProductRequest.getCount())
                    .build();

            orderDetail.setOrder(order);
        }

        return orderRepository.save(order).getId();
    }

    @Transactional
    public void orderCancel(String userId, Long orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId);

        if (order == null) {
            throw new IllegalArgumentException("취소하는 상품이 존재하지 않습니다.");
        }

        order.cancel();
    }

    private boolean emptyData(List<OrderProductRequest> data) {
        return data == null || data.isEmpty();
    }
}
