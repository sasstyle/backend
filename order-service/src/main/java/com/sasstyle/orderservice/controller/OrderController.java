package com.sasstyle.orderservice.controller;

import com.sasstyle.orderservice.controller.dto.OrderIdResponse;
import com.sasstyle.orderservice.controller.dto.OrderRequest;
import com.sasstyle.orderservice.controller.dto.OrderResponse;
import com.sasstyle.orderservice.controller.dto.Result;
import com.sasstyle.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Result> findOrders(@RequestHeader String userId) {
        List<OrderResponse> orderResponses = orderService.findAllByOrderer(userId).stream()
                .map(OrderResponse::new)
                .collect(toList());

        return ResponseEntity
                .ok(new Result(orderResponses));
    }

    @PostMapping
    public ResponseEntity<OrderIdResponse> order(@RequestHeader String userId, @RequestBody OrderRequest request) {
        Long orderId = orderService.order(userId, request);

        return ResponseEntity
                .ok(new OrderIdResponse(orderId));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> orderCancel(@RequestHeader String userId, @PathVariable Long orderId) {
        orderService.orderCancel(userId, orderId);

        return ResponseEntity
                .ok()
                .build();
    }
}
