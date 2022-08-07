package com.sasstyle.orderservice.service;

import com.sasstyle.orderservice.client.CartServiceClient;
import com.sasstyle.orderservice.client.dto.ProductResponse;
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
import java.util.Map;

import static com.sasstyle.orderservice.entity.OrderStatus.ORDER;
import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final CartServiceClient cartServiceClient;

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

        // 상품 주문
        Order order = Order.builder()
                .userId(userId)
                .address(request.getAddress())
                .status(ORDER)
                .build();

        Map<Long, List<OrderProductRequest>> productMap = getProductMap(request.getData());
        productMap.forEach((productId, values) -> {
            ProductResponse productResponse = productServiceClient.findByProductId(productId);

            int count = 0;
            for (OrderProductRequest value : values) {
                // 상품 수량 검증
                if (value.getCount() <= 0) {
                    throw new IllegalArgumentException("주문 상품의 수량이 0 이하일 수 없습니다.");
                }

                count += value.getCount();
            }

            // 주문 상세
            OrderDetail orderDetail = OrderDetail.builder()
                    .productId(productId)
                    .profileUrl(productResponse.getProfileUrl())
                    .productName(productResponse.getName())
                    .orderPrice(productResponse.getPrice())
                    .count(count)
                    .build();

            orderDetail.setOrder(order);
        });

        // 장바구니 삭제
        cartServiceClient.deleteCart(userId);

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

    private Map<Long, List<OrderProductRequest>> getProductMap(List<OrderProductRequest> data) {
        return data.stream()
                .collect(groupingBy(OrderProductRequest::getProductId));
    }

    private boolean emptyData(List<OrderProductRequest> data) {
        return data == null || data.isEmpty();
    }
}
