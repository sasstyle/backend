package com.sasstyle.orderservice;

import com.sasstyle.orderservice.client.dto.ProductResponse;
import com.sasstyle.orderservice.controller.dto.OrderProductRequest;
import com.sasstyle.orderservice.controller.dto.OrderRequest;
import com.sasstyle.orderservice.entity.Order;
import com.sasstyle.orderservice.entity.OrderDetail;

import static com.sasstyle.orderservice.ProductDummy.*;
import static com.sasstyle.orderservice.UserDummy.ADDRESS;
import static com.sasstyle.orderservice.UserDummy.USER_ID;
import static com.sasstyle.orderservice.entity.OrderStatus.ORDER;

public class OrderDummy {

    public static Order testOrder(Long id, OrderRequest request) {
        Order order = Order.builder()
                .id(id)
                .userId(USER_ID)
                .status(ORDER)
                .address(ADDRESS)
                .build();

        for (int i = 0; i < request.getData().size(); i++) {
            OrderProductRequest orderProductRequest = request.getData().get(i);
            ProductResponse productResponse = new ProductResponse(PROFILE_URL.get(0), NAME.get(0), PRICE.get(0));

            OrderDetail orderDetail = OrderDetail.builder()
                    .productId(orderProductRequest.getProductId())
                    .profileUrl(productResponse.getProfileUrl())
                    .productName(productResponse.getName())
                    .orderPrice(productResponse.getPrice())
                    .count(orderProductRequest.getCount())
                    .build();

            orderDetail.setOrder(order);
        }

        return order;
    }
}
