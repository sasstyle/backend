package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.ProductResponse;
import com.sasstyle.productservice.controller.dto.WishIdResponse;
import com.sasstyle.productservice.controller.dto.WishRequest;
import com.sasstyle.productservice.service.ProductWishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/wishs")
public class ProductWishController {

    private final ProductWishService productWishService;

    @GetMapping("/me")
    public ResponseEntity<Page<ProductResponse>> wishList(@RequestHeader String userId, Pageable pageable) {
        return ResponseEntity
                .ok(productWishService.findWishList(userId, pageable));
    }

    @PostMapping
    public ResponseEntity<WishIdResponse> wish(@RequestHeader String userId, @RequestBody WishRequest request) {
        return ResponseEntity
                .ok(new WishIdResponse(productWishService.wish(userId, request.getProductId())));
    }

    @DeleteMapping
    public ResponseEntity<Void> unWish(@RequestHeader String userId, @RequestBody WishRequest request) {
        productWishService.unWish(userId, request.getProductId());

        return ResponseEntity
                .ok()
                .build();
    }
}
