package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.*;
import com.sasstyle.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "모든 상품 조회", description = "모든 상품 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> products(@RequestHeader(required = false) String userId, Pageable pageable) {
        return ResponseEntity
                .ok(productService.findProducts(userId, pageable));
    }

    @Operation(summary = "상품 조회", description = "상품 아이디에 해당하는 상품을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "400", description = "상품 아이디에 해당하는 상품이 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> product(@RequestHeader(required = false) String userId, @PathVariable Long productId) {
        return ResponseEntity
                .ok(productService.findProductWithWish(userId, productId));
    }

    @Operation(summary = "상품 이름 검색", description = "상품 이름을 포함하는 상품 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> search(@RequestHeader(required = false) String userId, @ModelAttribute ProductSearch productSearch, Pageable pageable) {
        return ResponseEntity
                .ok(productService.search(userId, productSearch, pageable));
    }

    @Operation(summary = "상품 이름 자동 완성", description = "상품 이름을 검색할때 자동 완성 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    @GetMapping("/search/autocomplete")
    public ResponseEntity<Result<List<ProductSimpleResponse>>> autocomplete(@ModelAttribute ProductSearch productSearch, Pageable pageable) {
        return ResponseEntity
                .ok(new Result(productService.findAllSimple(productSearch, pageable)));
    }

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 아이디에 해당하는 카테고리가 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @PostMapping
    public ResponseEntity<ProductIdResponse> createProduct(@RequestHeader String userId, @RequestBody ProductRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(new ProductIdResponse(productService.createProduct(userId, request)));
    }

    @Operation(summary = "상품 수정", description = "상품 아이디에 해당하는 상품을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "400", description = "상품 아이디에 해당하는 상품이 존재하지 않는 경우에 발생할 수 있습니다.")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@RequestHeader String userId,
                                              @PathVariable Long productId,
                                              @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(userId, productId, request);

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
    public ResponseEntity<Void> deleteProduct(@RequestHeader String userId, @PathVariable Long productId) {
        productService.deleteProduct(userId, productId);

        return ResponseEntity
                .ok()
                .build();
    }
}
