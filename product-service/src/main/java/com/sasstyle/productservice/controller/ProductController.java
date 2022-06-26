package com.sasstyle.productservice.controller;

import com.sasstyle.productservice.controller.dto.ProductDetailResponse;
import com.sasstyle.productservice.controller.dto.ProductIdResponse;
import com.sasstyle.productservice.controller.dto.ProductRequest;
import com.sasstyle.productservice.controller.dto.ProductUpdateRequest;
import com.sasstyle.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> product(@PathVariable Long productId) {
        return ResponseEntity
                .ok(new ProductDetailResponse(productService.findById(productId)));
    }

    @PostMapping
    public ResponseEntity<ProductIdResponse> createProduct(@RequestBody ProductRequest request) {
        return ResponseEntity
                .ok(new ProductIdResponse(productService.createProduct(request)));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(productId, request);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity
                .ok()
                .build();
    }
}
