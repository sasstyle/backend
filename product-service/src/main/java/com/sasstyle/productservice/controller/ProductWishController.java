package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.ProductDetailResponse;
import com.sasstyle.productservice.controller.dto.WishRequest;
import com.sasstyle.productservice.controller.dto.WishResponse;
import com.sasstyle.productservice.service.ProductService;
import com.sasstyle.productservice.service.ProductWishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final ProductService productService;

    @Operation(summary = "위시리스트 상품 목록", description = "사용자가 좋아요를 누른 상품 목록을 출력합니다.")
    @ApiResponse(responseCode = "200", description = "위시리스트 상품 목록 반환 성공")
    @GetMapping
    public ResponseEntity<Page<WishResponse>> wishList(@RequestHeader String userId, Pageable pageable) {
        return ResponseEntity
                .ok(productWishService.findAllWish(userId, pageable));
    }

    @Operation(summary = "상품 좋아요", description = "사용자가 원하는 상품을 좋아요를 누를 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "상품 좋아요 성공")
    @PostMapping
    public ResponseEntity<ProductDetailResponse> wish(@RequestHeader String userId, @RequestBody WishRequest request) {
        productWishService.wish(userId, request.getProductId());

        return ResponseEntity
                .ok(productService.findProductWithWish(userId, request.getProductId()));
    }

    @Operation(summary = "상품 좋아요 삭제", description = "사용자가 상품을 좋아요를 삭제할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "상품 좋아요 삭제 성공")
    @DeleteMapping
    public ResponseEntity<ProductDetailResponse> unWish(@RequestHeader String userId, @RequestBody WishRequest request) {
        productWishService.unWish(userId, request.getProductId());

        return ResponseEntity
                .ok(productService.findProductWithWish(userId, request.getProductId()));
    }
}
