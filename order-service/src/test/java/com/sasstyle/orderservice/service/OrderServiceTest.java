package com.sasstyle.orderservice.service;

import com.sasstyle.orderservice.OrderDummy;
import com.sasstyle.orderservice.client.CartServiceClient;
import com.sasstyle.orderservice.client.dto.ProductResponse;
import com.sasstyle.orderservice.client.ProductServiceClient;
import com.sasstyle.orderservice.controller.dto.OrderProductRequest;
import com.sasstyle.orderservice.controller.dto.OrderRequest;
import com.sasstyle.orderservice.entity.Order;
import com.sasstyle.orderservice.entity.OrderStatus;
import com.sasstyle.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.sasstyle.orderservice.ProductDummy.*;
import static com.sasstyle.orderservice.UserDummy.ADDRESS;
import static com.sasstyle.orderservice.UserDummy.USER_ID;
import static com.sasstyle.orderservice.entity.OrderStatus.ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @Mock
    private CartServiceClient cartServiceClient;

    public List<OrderProductRequest> testOrderProductRequest() {
        return List.of(new OrderProductRequest(PRODUCT_ID.get(0), 1),
                new OrderProductRequest(PRODUCT_ID.get(1),  1),
                new OrderProductRequest(PRODUCT_ID.get(2), 5));
    }

    public List<OrderProductRequest> testOneOrderProductRequest() {
        Long productId = 1L;

        return List.of(new OrderProductRequest(productId, 1));
    }

    public OrderRequest testOrderRequest(List<OrderProductRequest> orderProductRequests) {
        return new OrderRequest(orderProductRequests, ADDRESS);
    }

    @Test
    @DisplayName("상품 주문 - 1개")
    void 주문_1개() {
        // given
        Long orderId = 100L;

        OrderRequest orderRequest = testOrderRequest(testOneOrderProductRequest());
        Order orderDummy = OrderDummy.testOrder(orderId, orderRequest);
        given(orderRepository.save(any())).willReturn(orderDummy);
        given(productServiceClient.findByProductId(orderRequest.getData().get(0).getProductId()))
                .willReturn(new ProductResponse(PROFILE_URL.get(0), NAME.get(0), PRICE.get(0)));


        // when
        Long savedId = orderService.order(USER_ID, orderRequest);

        given(orderRepository.findById(savedId)).willReturn(Optional.of(orderDummy));

        Order order = orderService.findById(savedId);

        // then
        assertThat(savedId).isEqualTo(orderId);
        assertThat(order.getOrderDetails().size()).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(ORDER);
        assertThat(order.getAddress().getDetails()).isEqualTo(ADDRESS);
    }

    @Test
    @DisplayName("상품 주문 - 여러개")
    void 주문_여러개() {
        // given
        Long orderId = 100L;

        OrderRequest orderRequest = testOrderRequest(testOrderProductRequest());
        Order orderDummy = OrderDummy.testOrder(orderId, orderRequest);
        given(orderRepository.save(any())).willReturn(orderDummy);

        for (int i = 0; i < orderRequest.getData().size(); i++) {
            given(productServiceClient.findByProductId(orderRequest.getData().get(i).getProductId()))
                    .willReturn(new ProductResponse(PROFILE_URL.get(0), NAME.get(0), PRICE.get(0)));
        }

        // when
        Long savedId = orderService.order(USER_ID, orderRequest);

        given(orderRepository.findById(savedId)).willReturn(Optional.of(orderDummy));

        Order order = orderService.findById(savedId);

        // then
        assertThat(savedId).isEqualTo(orderId);
        assertThat(order.getOrderDetails().size()).isEqualTo(3L);
    }

    @DisplayName("주문 실패 - 주문 데이터가 NULL인 경우")
    @Test
    void 주문_실패_NULL() {
        assertThatThrownBy(() -> orderService.order(null, null))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("주문 실패 - 주문 데이터가 빈 경우")
    @Test
    void 주문_실패_빈() {
        assertThatThrownBy(() -> orderService.order(null, new OrderRequest()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 목록 조회")
    @Test
    void 주문_목록_조회() {
        // given
        Long orderId1 = 101L;
        Long orderId2 = 102L;

        OrderRequest orderRequest1 = testOrderRequest(testOrderProductRequest());
        OrderRequest orderRequest2 = testOrderRequest(testOrderProductRequest());

        given(orderRepository.save(any())).willReturn(OrderDummy.testOrder(orderId1, orderRequest1));
        given(orderRepository.save(any())).willReturn(OrderDummy.testOrder(orderId2, orderRequest2));

        for (int i = 0; i < orderRequest1.getData().size(); i++) {
            given(productServiceClient.findByProductId(orderRequest1.getData().get(i).getProductId()))
                    .willReturn(new ProductResponse(PROFILE_URL.get(0), NAME.get(0), PRICE.get(0)));
        }

        for (int i = 0; i < orderRequest2.getData().size(); i++) {
            given(productServiceClient.findByProductId(orderRequest2.getData().get(i).getProductId()))
                    .willReturn(new ProductResponse(PROFILE_URL.get(0), NAME.get(0), PRICE.get(0)));
        }

        // when
        orderService.order(USER_ID, orderRequest1);
        orderService.order(USER_ID, orderRequest2);

        given(orderRepository.findAllByUserId(USER_ID))
                .willReturn(List.of(OrderDummy.testOrder(orderId1, orderRequest1), OrderDummy.testOrder(orderId2, orderRequest2)));

        List<Order> orders = orderService.findAllByOrderer(USER_ID);

        // then
        assertThat(orders.size()).isEqualTo(2L);
        assertThat(orders.get(0).getOrderDetails().size()).isEqualTo(3L);
    }

    @DisplayName("주문 취소")
    @Test
    void 주문_취소() {
        // given
        Long orderId = 100L;

        OrderRequest orderRequest = testOrderRequest(testOneOrderProductRequest());
        Order orderDummy = OrderDummy.testOrder(orderId, orderRequest);
        given(orderRepository.save(any())).willReturn(orderDummy);
        given(productServiceClient.findByProductId(orderRequest.getData().get(0).getProductId()))
                .willReturn(new ProductResponse(PROFILE_URL.get(0), NAME.get(0), PRICE.get(0)));

        // when
        Long savedId = orderService.order(USER_ID, orderRequest);
        given(orderRepository.findByUserIdAndId(USER_ID, orderId)).willReturn(orderDummy);

        // 주문 취소
        orderService.orderCancel(USER_ID, savedId);
        given(orderRepository.findById(savedId)).willReturn(Optional.of(orderDummy));
        Order findOrder = orderService.findById(savedId);

        // then
        assertThat(findOrder.getId()).isEqualTo(orderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }
}