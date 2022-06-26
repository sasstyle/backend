package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.ProductDetailResponse;
import com.sasstyle.productservice.controller.dto.ProductIdResponse;
import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import com.sasstyle.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 조회", description = "상품 아이디에 해당하는 상품을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "400", description = "상품 아이디에 해당하는 상품이 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> product(@PathVariable Long productId) {
        return ResponseEntity
                .ok(new ProductDetailResponse(productService.findProduct(productId)));
    }

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 아이디에 해당하는 카테고리가 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @PostMapping
    public ResponseEntity<ProductIdResponse> createProduct(@RequestBody ProductRequest request) {
        return ResponseEntity
                .ok(new ProductIdResponse(productService.createProduct(request)));
    }

    @Operation(summary = "상품 수정", description = "상품 아이디에 해당하는 상품을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "400", description = "상품 아이디에 해당하는 상품이 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(productId, request);

        return ResponseEntity
                .ok()
                .build();
    }

    @Operation(summary = "상품 삭제", description = "상품 아이디에 해당하는 상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "상품 아이디에 해당하는 상품이 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity
                .ok()
                .build();
    }
}
