package com.sasstyle.cartservice.controller;

import com.sasstyle.cartservice.controller.dto.CartRequest;
import com.sasstyle.cartservice.controller.dto.CartResponse;
import com.sasstyle.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> carts(@RequestHeader String userId) {
        return ResponseEntity
                .ok(cartService.findCart(userId));
    }

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestHeader String userId, @RequestBody CartRequest request) {
        cartService.addCart(userId, request.getProductId(), request.getCount());

        return ResponseEntity
                .status(CREATED)
                .build();
    }

    @PutMapping("/detail/{cartDetailId}")
    public ResponseEntity<Void> updateCount(@RequestHeader String userId, @PathVariable Long cartDetailId, @RequestBody CartDetailUpdateRequest request) {
        cartService.updateCount(userId, cartDetailId, request.getCount());

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/detail/{cartDetailId}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable Long cartDetailId) {
        cartService.deleteCartDetail(cartDetailId);

        return ResponseEntity
                .ok()
                .build();
    }
}
