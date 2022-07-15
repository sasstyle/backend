package com.sasstyle.orderservice.controller;

import com.sasstyle.orderservice.controller.dto.OrderIdResponse;
import com.sasstyle.orderservice.controller.dto.OrderRequest;
import com.sasstyle.orderservice.controller.dto.OrderResponse;
import com.sasstyle.orderservice.controller.dto.Result;
import com.sasstyle.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "주문 목록 조회", description = "사용자 주문 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공")
    @GetMapping
    public ResponseEntity<Result<List<OrderResponse>>> findOrders(@RequestHeader String userId) {
        List<OrderResponse> orderResponses = orderService.findAllByOrderer(userId).stream()
                .map(OrderResponse::new)
                .collect(toList());

        return ResponseEntity
                .ok(new Result(orderResponses));
    }

    @Operation(summary = "상품 주문", description = "사용자가 상품을 주문합니다.")
    @ApiResponse(responseCode = "200", description = "상품 주문 성공")
    @PostMapping
    public ResponseEntity<OrderIdResponse> order(@RequestHeader String userId, @RequestBody OrderRequest request) {
        Long orderId = orderService.order(userId, request);

        return ResponseEntity
                .ok(new OrderIdResponse(orderId));
    }

    @Operation(summary = "주문 취소", description = "사용자가 상품 주문을 취소합니다.")
    @ApiResponse(responseCode = "200", description = "주문 취소 성공")
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> orderCancel(@RequestHeader String userId, @PathVariable Long orderId) {
        orderService.orderCancel(userId, orderId);

        return ResponseEntity
                .ok()
                .build();
    }
}
