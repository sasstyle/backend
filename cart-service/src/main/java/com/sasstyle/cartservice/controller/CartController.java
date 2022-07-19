package com.sasstyle.cartservice.controller;

import com.sasstyle.cartservice.controller.dto.CartDetailUpdateRequest;
import com.sasstyle.cartservice.controller.dto.CartDetailRequest;
import com.sasstyle.cartservice.controller.dto.CartResponse;
import com.sasstyle.cartservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 조회", description = "사용자 장바구니를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 조회 성공")
    @GetMapping
    public ResponseEntity<CartResponse> carts(@RequestHeader String userId) {
        return ResponseEntity
                .ok(cartService.findCart(userId));
    }

    @Operation(summary = "장바구니 상품 추가", description = "사용자 장바구니에 상품을 추가합니다.")
    @ApiResponse(responseCode = "201", description = "장바구니 상품 추가 성공")
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestHeader String userId, @RequestBody CartDetailRequest request) {
        cartService.addCart(userId, request.getProductId(), request.getCount());

        return ResponseEntity
                .status(CREATED)
                .build();
    }

    @Operation(summary = "장바구니 상품 수정", description = "장바구니에 있는 상품을 수량을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 상품 수정 성공")
    @PutMapping("/detail/{cartDetailId}")
    public ResponseEntity<Void> updateCount(@RequestHeader String userId, @PathVariable Long cartDetailId, @RequestBody CartDetailUpdateRequest request) {
        cartService.updateCount(userId, cartDetailId, request.getCount());

        return ResponseEntity
                .ok()
                .build();
    }

    @Operation(summary = "장바구니 전체 삭제", description = "장바구니 전체 항목을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 전체 삭제 성공")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);

        return ResponseEntity
                .ok()
                .build();
    }

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 등록된 상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 상품 삭제 성공")
    @DeleteMapping("/detail/{cartDetailId}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable Long cartDetailId) {
        cartService.deleteCartDetail(cartDetailId);

        return ResponseEntity
                .ok()
                .build();
    }
}
